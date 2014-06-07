/*
 * Copyright 2014 Minkyu Cho
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.web.servlet.view.trimou;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractTemplateView;
import org.trimou.Mustache;
import org.trimou.exception.MustacheException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.Map;

/**
 * This is the spring view use to generate the content based on
 * a Mustache template.
 *
 * @author Minkyu Cho
 */
public class TrimouView extends AbstractTemplateView {
	private Mustache template;

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType(getContentType());
		final Writer writer = response.getWriter();
		try {
			template.render(writer, model);
		} finally {
			writer.flush();
		}
	}

	public void setTemplate(Mustache template) {
		this.template = template;
	}
}
