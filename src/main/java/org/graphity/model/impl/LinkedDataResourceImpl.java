/*
 * Copyright (C) 2012 Martynas Jusevičius <martynas@graphity.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.graphity.model.impl;

import com.hp.hpl.jena.rdf.model.Model;
import org.graphity.model.LinkedDataResource;
import org.graphity.util.manager.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Martynas Jusevičius <martynas@graphity.org>
 */
public class LinkedDataResourceImpl implements LinkedDataResource
{
    private static final Logger log = LoggerFactory.getLogger(LinkedDataResourceImpl.class);

    private String uri = null;
    private Model model = null;
    
    public LinkedDataResourceImpl(String uri)
    {
	if (uri == null) throw new IllegalArgumentException("Linked Data URI must be not null");
	this.uri = uri;
	log.debug("URI: {}", uri);
    }

    @Override
    public Model getModel()
    {
	if (model == null)
	{
	    log.debug("Loading Model from URI: {}", getURI());
	    model = DataManager.get().loadModel(getURI());

	    log.debug("Number of Model stmts read: {}", model.size());
	}

	return model;
    }

    @Override
    public String getURI()
    {
	return uri;
    }

}
