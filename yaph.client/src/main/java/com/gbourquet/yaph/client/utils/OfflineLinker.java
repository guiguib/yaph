package com.gbourquet.yaph.client.utils;

import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.AbstractLinker;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.core.ext.linker.EmittedArtifact;
import com.google.gwt.core.ext.linker.EmittedArtifact.Visibility;
import com.google.gwt.core.ext.linker.LinkerOrder;
import com.google.gwt.core.ext.linker.LinkerOrder.Order;

@LinkerOrder(Order.POST) 
public class OfflineLinker extends AbstractLinker {
	@Override
	public String getDescription() {
		return "Offline Linker";
	}
	
	 @Override
	  public ArtifactSet link(TreeLogger logger, LinkerContext context,ArtifactSet artifacts) throws UnableToCompleteException {
	    ArtifactSet artifactset = new ArtifactSet(artifacts);

	    StringBuilder builder= new StringBuilder("CACHE MANIFEST\n");
	    builder.append("# Cache Version 10\n");
	    builder.append("CACHE:\n");

	    builder.append("../css/main.css\n");
	    builder.append("../Yaph.html\n");    
	    for(EmittedArtifact emitted: artifacts.find(EmittedArtifact.class))
	    {
	    	if(emitted.getVisibility().equals(Visibility.Private))
	    	{
	    		continue;
	    	}
	    	if(emitted.getPartialPath().endsWith(".symbolMap"))continue;
	    	if(emitted.getPartialPath().endsWith(".txt"))continue;
	    	builder.append("" + emitted.getPartialPath()).append("\n");
	    }
	    builder.append("yaph.nocache.js\n");
	    builder.append("NETWORK:\n");
	    builder.append("*\n");
	    EmittedArtifact manifest= emitString(logger, builder.toString(), "offline.appcache");
	    artifactset.add(manifest);
	    return artifactset;
	  }
} 
