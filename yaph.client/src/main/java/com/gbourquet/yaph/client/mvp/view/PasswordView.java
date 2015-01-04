package com.gbourquet.yaph.client.mvp.view;

import java.util.Comparator;
import java.util.List;

import com.gbourquet.yaph.client.mvp.presenter.PasswordPresenter;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;

public class PasswordView extends Composite implements PasswordPresenter.View {

	private static PasswordViewUiBinder uiBinder = GWT
			.create(PasswordViewUiBinder.class);

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
	SimplePager pager;

	@UiField
	DialogBox newPasswordBox;

	@UiField
	Label newBoxError;

	@UiField
	TextBox newBoxTitre;

	@UiField
	TextBox newBoxUser;

	@UiField
	TextBox newBoxPassword;

	@UiField
	TextBox newBoxUrl;

	@UiField
	Button newBoxGenerateButton;

	@UiField
	Button newBoxValidButton;

	@UiField
	Button newBoxCancelButton;

	/**
	 * Column displays title.
	 */
	private Column<PasswordCard, String> titleColumn;

	/**
	 * Column displays user.
	 */
	private Column<PasswordCard, String> userColumn;

	/**
	 * Column displays password.
	 */
	private Column<PasswordCard, String> passwordColumn;

	/**
	 * Column displays address.
	 */
	private Column<PasswordCard, String> addressColumn;

	public interface TableRes extends DataGrid.Resources {
		@Source({DataGrid.Style.DEFAULT_CSS, "CwCustomDataGrid.css"})
		DataGridStyle dataGridStyle();
		 
		interface DataGridStyle extends DataGrid.Style {}
		}
	
	//CellTable custom UI resource
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

		dataGrid = new DataGrid<PasswordCard>(20,tableRes,KEY_PROVIDER);
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
		ListHandler<PasswordCard> sortHandler = new ListHandler<PasswordCard>(
				dataProvider.getList());
		dataGrid.addColumnSortHandler(sortHandler);

		// Create a Pager to control the table.
		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,
				true);
		pager.setDisplay(dataGrid);

		// Add a selection model so we can select cells.
		final NoSelectionModel<PasswordCard> selectionModel = new NoSelectionModel<PasswordCard>();
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						// final PasswordCard passwordCard =
						// selectionModel.getLastSelectedObject();

					}
				});

		dataGrid.setSelectionModel(selectionModel);

		/*
		 * final SelectionModel<PasswordCard> selectionModel = new
		 * MultiSelectionModel<PasswordCard>( KEY_PROVIDER);
		 */
		/*
		 * dataGrid.setSelectionModel(selectionModel,
		 * DefaultSelectionEventManager .<PasswordCard>
		 * createCheckboxManager());
		 */

		// Initialize the columns.
		initializeColumns(sortHandler);

		// Specify a custom table.
		dataGrid.setTableBuilder(new CustomTableBuilder());
		dataGrid.setHeaderBuilder(new CustomHeaderBuilder());
		dataGrid.setFooterBuilder(new CustomFooterBuilder());

		// Add the CellList to the adapter in the database.
		dataProvider.addDataDisplay(dataGrid);

		initWidget(uiBinder.createAndBindUi(this));

		newBoxTitre.getElement().setPropertyString("placeholder", "Titre");
		newBoxUser.getElement().setPropertyString("placeholder", "User");
		newBoxPassword.getElement()
				.setPropertyString("placeholder", "Password");
		newBoxUrl.getElement().setPropertyString("placeholder", "URL");

		newPasswordBox.center();
		newPasswordBox.hide();
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
	private class CustomHeaderBuilder extends
			AbstractHeaderOrFooterBuilder<PasswordCard> {

		private Header<String> titleHeader = new TextHeader("Titre");
		private Header<String> userHeader = new TextHeader("User");
		private Header<String> passwordHeader = new TextHeader("Password");
		private Header<String> addressHeader = new TextHeader("Adresse");

		public CustomHeaderBuilder() {
			super(dataGrid, false);
			setSortIconStartOfLine(false);
		}

		@Override
		protected boolean buildHeaderOrFooterImpl() {

			TableRowBuilder tr = startRow();
			/*TableCellBuilder th = tr.startTH();
			enableColumnHandlers(th, titleColumn);
			th.style().trustedProperty("border-right", "10px solid white")
					.cursor(Cursor.POINTER).endStyle();
			th.startH2().text("Titre").endH2();
			th.endTH();

			// Information group header.
			th = tr.startTH().colSpan(3);
			th.startH2().text("Informations").endH2();
			th.endTH();
*/
			// Get information about the sorted column.
			ColumnSortList sortList = dataGrid.getColumnSortList();
			ColumnSortInfo sortedInfo = (sortList.size() == 0) ? null
					: sortList.get(0);
			Column<?, ?> sortedColumn = (sortedInfo == null) ? null
					: sortedInfo.getColumn();
			boolean isSortAscending = (sortedInfo == null) ? false : sortedInfo
					.isAscending();

			// Add column headers.
			tr = startRow();
			buildHeader(tr, titleHeader, titleColumn, sortedColumn,
					isSortAscending, false, false);
			buildHeader(tr, userHeader, userColumn, sortedColumn,
					isSortAscending, false, false);
			buildHeader(tr, passwordHeader, passwordColumn, sortedColumn,
					isSortAscending, false, false);
			buildHeader(tr, addressHeader, addressColumn, sortedColumn,
					isSortAscending, false, true);
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
		private void buildHeader(TableRowBuilder out, Header<?> header,
				Column<PasswordCard, ?> column, Column<?, ?> sortedColumn,
				boolean isSortAscending, boolean isFirst, boolean isLast) {
			// Choose the classes to include with the element.
			com.google.gwt.user.cellview.client.AbstractCellTable.Style style = dataGrid
					.getResources().style();
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
				classesBuilder.append(" "
						+ (isSortAscending ? style.sortedHeaderAscending()
								: style.sortedHeaderDescending()));
			}

			// Create the table cell.
			TableCellBuilder th = out.startTH().className(
					classesBuilder.toString());

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
	 * Renders custom table footers that appear beneath the columns in the
	 * table. This footer consists of a single cell containing the average age
	 * of all contacts on the current page. This is an example of a dynamic
	 * footer that changes with the row data in the table.
	 */
	private class CustomFooterBuilder extends
			AbstractHeaderOrFooterBuilder<PasswordCard> {

		public CustomFooterBuilder() {
			super(dataGrid, true);
		}

		@Override
		protected boolean buildHeaderOrFooterImpl() {
			return true;
		}
	}

	/**
	 * Renders the data rows that display each contact in the table.
	 */
	private class CustomTableBuilder extends
			AbstractCellTableBuilder<PasswordCard> {

		private final String rowStyle;
		private final String selectedRowStyle;
		private final String cellStyle;
		private final String selectedCellStyle;

		public CustomTableBuilder() {
			super(dataGrid);

			// Cache styles for faster access.
			com.google.gwt.user.cellview.client.AbstractCellTable.Style style = dataGrid
					.getResources().style();
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
			SelectionModel<? super PasswordCard> selectionModel = dataGrid
					.getSelectionModel();
			boolean isSelected = (selectionModel == null || rowValue == null) ? false
					: selectionModel.isSelected(rowValue);
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

			// User column.
			td = row.startTD();
			td.className(cellStyles);
			td.style().outlineStyle(OutlineStyle.NONE).endStyle();
			renderCell(td, createContext(2), userColumn, rowValue);
			td.endTD();

			// Password column.
			td = row.startTD();
			td.className(cellStyles);
			td.style().outlineStyle(OutlineStyle.NONE).endStyle();
			renderCell(td, createContext(3), passwordColumn, rowValue);
			td.endTD();

			// Address column.
			td = row.startTD();
			td.className(cellStyles);
			td.style().outlineStyle(OutlineStyle.NONE).endStyle();
			renderCell(td, createContext(4), addressColumn, rowValue);
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

		// user.
		userColumn = new Column<PasswordCard, String>(new TextCell()) {
			@Override
			public String getValue(PasswordCard object) {
				return object.getUser();
			}
		};
		userColumn.setSortable(true);
		userColumn.setHorizontalAlignment(HasAlignment.ALIGN_LEFT);
		sortHandler.setComparator(userColumn, new Comparator<PasswordCard>() {
			@Override
			public int compare(PasswordCard o1, PasswordCard o2) {
				return o1.getUser().compareTo(o2.getUser());
			}
		});
		userColumn.setFieldUpdater(new FieldUpdater<PasswordCard, String>() {
			@Override
			public void update(int index, PasswordCard object, String value) {
				// Called when the user changes the value.
				object.setUser(value);
				dataProvider.refresh();
			}
		});
		dataGrid.setColumnWidth(1, 20, Unit.PCT);

		// Password.
		passwordColumn = new Column<PasswordCard, String>(new TextCell()) {
			@Override
			public String getValue(PasswordCard object) {
				return object.getPassword();
			}
		};
		passwordColumn.setSortable(true);
		passwordColumn.setHorizontalAlignment(HasAlignment.ALIGN_LEFT);
		sortHandler.setComparator(passwordColumn,
				new Comparator<PasswordCard>() {
					@Override
					public int compare(PasswordCard o1, PasswordCard o2) {
						return o1.getPassword().compareTo(o2.getPassword());
					}
				});
		dataGrid.setColumnWidth(2, 20, Unit.PCT);

		// Address.
		addressColumn = new Column<PasswordCard, String>(new TextCell()) {
			@Override
			public String getValue(PasswordCard object) {
				return object.getAdresse();
			}
		};
		addressColumn.setSortable(true);
		addressColumn.setHorizontalAlignment(HasAlignment.ALIGN_LEFT);
		sortHandler.setComparator(addressColumn,
				new Comparator<PasswordCard>() {
					@Override
					public int compare(PasswordCard o1, PasswordCard o2) {
						return o1.getAdresse().compareTo(o2.getAdresse());
					}
				});
		dataGrid.setColumnWidth(3, 40, Unit.PCT);
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
	public void showNewPasswordBox() {
		newPasswordBox.center();
		newPasswordBox.setVisible(true);
		newPasswordBox.show();
	}

	@Override
	public HasClickHandlers getCancelNewPasswordButton() {

		return newBoxCancelButton;
	}

	@Override
	public void closeNewPasswordBox() {
		newPasswordBox.hide();
	}

	@Override
	public void clearNewPasswordBox() {
		newBoxTitre.setText("");
		newBoxUser.setText("");
		newBoxPassword.setText("");
		newBoxUrl.setText("");
	}

	@Override
	public HasClickHandlers getValidNewPasswordButton() {
		return newBoxValidButton;
	}

	@Override
	public String getTitleText() {
		return newBoxTitre.getText();
	}

	@Override
	public String getUserText() {
		return newBoxUser.getText();
	}

	@Override
	public String getPasswordText() {
		return newBoxPassword.getText();
	}

	@Override
	public String getAdressText() {
		return newBoxUrl.getText();
	}

	@Override
	public Label getErrorNewPasswordLabel() {
		return newBoxError;
	}

	@Override
	public void updatePasswordList(List<PasswordCard> passwords) {
		List<PasswordCard> data = dataProvider.getList();
		data.clear();
		data.addAll(passwords);
	}

	@Override
	public HasClickHandlers getGeneratePasswordButton() {
		return newBoxGenerateButton;
	}

	@Override
	public void setPasswordText(String password) {
		newBoxPassword.setText(password);
	}
}
