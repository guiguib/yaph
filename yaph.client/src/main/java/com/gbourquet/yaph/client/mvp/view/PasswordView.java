package com.gbourquet.yaph.client.mvp.view;

import java.util.Comparator;
import java.util.List;

import com.gbourquet.yaph.client.mvp.presenter.PasswordPresenter;
import com.gbourquet.yaph.client.widget.NormalPager;
import com.gbourquet.yaph.client.widget.PasswordWidget;
import com.gbourquet.yaph.client.widget.PasswordWidget.TypePassword;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.builder.shared.TableCellBuilder;
import com.google.gwt.dom.builder.shared.TableRowBuilder;
import com.google.gwt.dom.client.Style.OutlineStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.AbstractCellTableBuilder;
import com.google.gwt.user.cellview.client.AbstractHeaderOrFooterBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public class PasswordView extends Composite implements PasswordPresenter.View {

	private static PasswordViewUiBinder uiBinder = GWT.create(PasswordViewUiBinder.class);

	interface PasswordViewUiBinder extends UiBinder<Widget, PasswordView> {
	}

	/**
	 * The main DataGrid.
	 */
	@UiField(provided = true)
	DataGrid<PasswordCard> dataGrid;

	ListDataProvider<PasswordCard> dataProvider;

	/**
	 * The "new" Button.
	 */
	@UiField
	Button newPasswordButton;

	/**
	 * The pager used to change the range of data.
	 */
	@UiField(provided = true)
	NormalPager pager;

	@UiField
	SimplePanel detailPassword;

	@UiField
	Button updatePasswordButton;

	@UiField
	Button deletePasswordButton;

	VerticalPanel fields;

	/**
	 * Column displays title.
	 */
	private Column<PasswordCard, String> titleColumn;
	private SingleSelectionModel<PasswordCard> selectionModel;

	public interface TableRes extends DataGrid.Resources {
		@Source({ DataGrid.Style.DEFAULT_CSS, "CwCustomDataGrid.css" })
		DataGridStyle dataGridStyle();

		interface DataGridStyle extends DataGrid.Style {
		}
	}

	// CellTable custom UI resource
	private DataGrid.Resources tableRes = GWT.create(TableRes.class);

	public PasswordView() {

		// Create a DataGrid.

		/*
		 * Set a key provider that provides a unique key for each PasswordCard.
		 * If key is used to identify PasswordCard when fields (such as the url
		 * and login) change.
		 */
		ProvidesKey<PasswordCard> KEY_PROVIDER = new ProvidesKey<PasswordCard>() {
			@Override
			public Object getKey(PasswordCard item) {
				return item == null ? null : item.getId();
			}
		};

		dataGrid = new DataGrid<PasswordCard>(20, tableRes, KEY_PROVIDER);
		dataGrid.setWidth("100%");

		/*
		 * Do not refresh the headers every time the data is updated. The footer
		 * depends on the current data, so we do not disable auto refresh on the
		 * footer.
		 */
		dataGrid.setAutoHeaderRefreshDisabled(true);

		// Set the message to display when the table is empty.
		dataGrid.setEmptyTableWidget(new Label("Vide"));

		// Attach a column sort handler to the ListDataProvider to sort the
		// list.
		dataProvider = new ListDataProvider<PasswordCard>();
		ListHandler<PasswordCard> sortHandler = new ListHandler<PasswordCard>(dataProvider.getList());
		dataGrid.addColumnSortHandler(sortHandler);

		// Create a Pager to control the table.
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new NormalPager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);

		// Add a selection model so we can select cells.
		selectionModel = new SingleSelectionModel<PasswordCard>();
		selectionModel.getSelectedObject();

		dataGrid.setSelectionModel(selectionModel);

		// Initialize the columns.
		initializeColumns(sortHandler);

		// Specify a custom table.
		dataGrid.setTableBuilder(new CustomTableBuilder());
		dataGrid.setHeaderBuilder(new CustomHeaderBuilder());

		// Add the CellList to the adapter in the database.
		dataProvider.addDataDisplay(dataGrid);

		initWidget(uiBinder.createAndBindUi(this));
		fields = new VerticalPanel();
		detailPassword.add(fields);
		setFieldsVisible(false);

	}

	/**
	 * The UiBinder interface used by this example.
	 */
	interface Binder extends UiBinder<Widget, PasswordView> {
	}

	/**
	 * Renders custom table headers. The top header row includes the groups
	 * "Name" and "Information", each of which spans multiple columns. The
	 * second row of the headers includes the contacts' first and last names
	 * grouped under the "Name" category. The second row also includes the age,
	 * category, and address of the contacts grouped under the "Information"
	 * category.
	 */
	private class CustomHeaderBuilder extends AbstractHeaderOrFooterBuilder<PasswordCard> {

		private Header<String> titleHeader = new TextHeader("Titre");

		public CustomHeaderBuilder() {
			super(dataGrid, false);
			setSortIconStartOfLine(false);
		}

		@Override
		protected boolean buildHeaderOrFooterImpl() {

			TableRowBuilder tr = startRow();
			ColumnSortList sortList = dataGrid.getColumnSortList();
			ColumnSortInfo sortedInfo = (sortList.size() == 0) ? null : sortList.get(0);
			Column<?, ?> sortedColumn = (sortedInfo == null) ? null : sortedInfo.getColumn();
			boolean isSortAscending = (sortedInfo == null) ? false : sortedInfo.isAscending();

			// Add column headers.
			tr = startRow();
			buildHeader(tr, titleHeader, titleColumn, sortedColumn, isSortAscending, false, false);
			tr.endTR();

			return true;
		}

		/**
		 * Renders the header of one column, with the given options.
		 * 
		 * @param out
		 *            the table row to build into
		 * @param header
		 *            the {@link Header} to render
		 * @param column
		 *            the column to associate with the header
		 * @param sortedColumn
		 *            the column that is currently sorted
		 * @param isSortAscending
		 *            true if the sorted column is in ascending order
		 * @param isFirst
		 *            true if this the first column
		 * @param isLast
		 *            true if this the last column
		 */
		private void buildHeader(TableRowBuilder out, Header<?> header, Column<PasswordCard, ?> column, Column<?, ?> sortedColumn, boolean isSortAscending, boolean isFirst,
				boolean isLast) {
			// Choose the classes to include with the element.
			com.google.gwt.user.cellview.client.AbstractCellTable.Style style = dataGrid.getResources().style();
			boolean isSorted = (sortedColumn == column);
			StringBuilder classesBuilder = new StringBuilder(style.header());
			if (isFirst) {
				classesBuilder.append(" " + style.firstColumnHeader());
			}
			if (isLast) {
				classesBuilder.append(" " + style.lastColumnHeader());
			}
			if (column.isSortable()) {
				classesBuilder.append(" " + style.sortableHeader());
			}
			if (isSorted) {
				classesBuilder.append(" " + (isSortAscending ? style.sortedHeaderAscending() : style.sortedHeaderDescending()));
			}

			// Create the table cell.
			TableCellBuilder th = out.startTH().className(classesBuilder.toString());

			// Associate the cell with the column to enable sorting of the
			// column.
			enableColumnHandlers(th, column);

			// Render the header.
			Context context = new Context(0, 0, header.getKey());
			renderSortableHeader(th, context, header, isSorted, isSortAscending);

			// End the table cell.
			th.endTH();
		}
	}

	/**
	 * Renders the data rows that display each contact in the table.
	 */
	private class CustomTableBuilder extends AbstractCellTableBuilder<PasswordCard> {

		private final String rowStyle;
		private final String selectedRowStyle;
		private final String cellStyle;
		private final String selectedCellStyle;

		public CustomTableBuilder() {
			super(dataGrid);

			// Cache styles for faster access.
			com.google.gwt.user.cellview.client.AbstractCellTable.Style style = dataGrid.getResources().style();
			rowStyle = style.evenRow();
			selectedRowStyle = " " + style.selectedRow();
			cellStyle = style.cell() + " " + style.evenRowCell();
			selectedCellStyle = " " + style.selectedRowCell();

		}

		@Override
		public void buildRowImpl(PasswordCard rowValue, int absRowIndex) {
			buildPasswordCardRow(rowValue, absRowIndex);

		}

		/**
		 * Build a row.
		 * 
		 * @param rowValue
		 *            the contact info
		 * @param absRowIndex
		 *            the absolute row index
		 * @param isFriend
		 *            true if this is a subrow, false if a top level row
		 */
		private void buildPasswordCardRow(PasswordCard rowValue, int absRowIndex) {
			// Calculate the row styles.
			SelectionModel<? super PasswordCard> selectionModel = dataGrid.getSelectionModel();
			boolean isSelected = (selectionModel == null || rowValue == null) ? false : selectionModel.isSelected(rowValue);
			StringBuilder trClasses = new StringBuilder(rowStyle);
			if (isSelected) {
				trClasses.append(selectedRowStyle);
			}

			// Calculate the cell styles.
			String cellStyles = cellStyle;
			if (isSelected) {
				cellStyles += selectedCellStyle;
			}

			TableRowBuilder row = startRow();
			row.className(trClasses.toString());

			// Title column.
			TableCellBuilder td = row.startTD();
			td.className(cellStyles);
			td.style().outlineStyle(OutlineStyle.NONE).endStyle();
			renderCell(td, createContext(1), titleColumn, rowValue);
			td.endTD();

			row.endTR();
		}
	}

	/**
	 * Defines the columns in the custom table. Maps the data in the
	 * PasswordCard for each row into the appropriate column in the table, and
	 * defines handlers for each column.
	 */
	private void initializeColumns(ListHandler<PasswordCard> sortHandler) {

		// Title.
		titleColumn = new Column<PasswordCard, String>(new TextCell()) {
			@Override
			public String getValue(PasswordCard object) {
				return object.getTitre();
			}
		};
		titleColumn.setHorizontalAlignment(HasAlignment.ALIGN_LEFT);
		titleColumn.setSortable(true);
		sortHandler.setComparator(titleColumn, new Comparator<PasswordCard>() {
			@Override
			public int compare(PasswordCard o1, PasswordCard o2) {
				return o1.getTitre().compareTo(o2.getTitre());
			}
		});
		titleColumn.setFieldUpdater(new FieldUpdater<PasswordCard, String>() {
			@Override
			public void update(int index, PasswordCard object, String value) {
				// Called when the user changes the value.
				object.setTitre(value);
				dataProvider.refresh();
			}
		});
		dataGrid.setColumnWidth(0, 25, Unit.PCT);

	}

	@Override
	public HasClickHandlers getNewPasswordButton() {
		return newPasswordButton;
	}

	@Override
	public void addPassword(PasswordCard password) {
		List<PasswordCard> passwords = dataProvider.getList();
		passwords.add(password);
	}

	@Override
	public void removePassword(PasswordCard password) {
		List<PasswordCard> passwords = dataProvider.getList();
		passwords.remove(password);
	}

	@Override
	public void updatePasswordList(List<PasswordCard> passwords) {
		List<PasswordCard> data = dataProvider.getList();
		data.clear();
		data.addAll(passwords);
	}

	@Override
	public void addSelectionChangeHandler(Handler handler) {
		selectionModel.addSelectionChangeHandler(handler);
	}

	@Override
	public void addField(PasswordField field) {
		TypePassword type = "PASSWD".equals(field.getType()) ? TypePassword.PASSWD : TypePassword.TEXT; // TODO
																										// gérer
																										// tous
																										// les
																										// types
		PasswordWidget fieldWidget = new PasswordWidget(type);
		fieldWidget.setTitleText(field.getLibelle());
		fieldWidget.setValueText(field.getValue());
		fields.add(fieldWidget);
	}

	@Override
	public PasswordCard getSelectedPassword() {
		return selectionModel.getSelectedObject();
	}

	@Override
	public void setFieldsVisible(Boolean isVisible) {
		detailPassword.setVisible(isVisible);
		updatePasswordButton.setVisible(isVisible);
		deletePasswordButton.setVisible(isVisible);
	}

	@Override
	public void clearFields() {
		detailPassword.remove(fields);
		fields = new VerticalPanel();
		detailPassword.add(fields);
	}

	@Override
	public void unselectPassword() {
		selectionModel.clear();
		detailPassword.setVisible(false);
		updatePasswordButton.setVisible(false);
		deletePasswordButton.setVisible(false);
	}

	@Override
	public void selectPassword(PasswordCard password) {
		selectionModel.setSelected(password, true);
		pager.lastPage();
	}

	@Override
	public HasClickHandlers getUpdatePasswordButton() {
		return updatePasswordButton;
	}

	@Override
	public HasClickHandlers getDeletePasswordButton() {
		return deletePasswordButton;
	}

}
