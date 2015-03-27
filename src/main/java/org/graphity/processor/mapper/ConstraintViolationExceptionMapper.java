/*
 * Copyright 2013 Martynas Jusevičius <martynas@graphity.org>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.graphity.processor.mapper;

import com.hp.hpl.jena.vocabulary.RDF;
import java.net.URI;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import org.graphity.processor.exception.ConstraintViolationException;
import org.graphity.processor.util.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topbraid.spin.constraints.SPINConstraints;

/**
 *
 * @author Martynas Jusevičius <martynas@graphity.org>
 */
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException>
{
    private static final Logger log = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);
    
    @Override
    public Response toResponse(ConstraintViolationException cve)
    {
	//if (log.isDebugEnabled()) log.debug("ConstraintViolation root: {} source: {}", e.getConstraintViolation().getRoot(), e.getConstraintViolation().getSource());

        SPINConstraints.addConstraintViolationsRDF(cve.getConstraintViolations(), cve.getModel(), true);
        Link classLink = new Link(URI.create(cve.getMatchedOntClass().getURI()), RDF.type.getLocalName(), null);
	
	return Response.ok(cve). // ok(e.getModel()).
		status(Response.Status.BAD_REQUEST).
                header("Link", classLink.toString()).
		build();
    }
    
}
