package com.wcs.compare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wcs.compare.enums.HorizontalOrientationEnum;
import com.wcs.compare.request.util.JSONObject;
import com.wcs.compare.service.DiffService;

/**
 * 
 * Application Rest controller
 *
 * @author <a href="mailto:wellington.cs@hotmail.com">Wellington Silva</a>.
 * @version $Revision: 1.0 $
 */
@RestController
@RequestMapping("/v1/diff/{id}")
public class DiffController {

	private static final Logger LOG = LoggerFactory.getLogger(DiffController.class);

	@Autowired
	private DiffService service;

	/**
	 * 
	 * Left side comparison
	 *
	 * @author <a href="mailto:wellington.cs@hotmail.com">Wellington Silva</a>.
	 * @param id   unique identifier
	 * @param data string base64
	 * @return String message response
	 * @throws Exception
	 */
	@RequestMapping(value = "/left", method = RequestMethod.POST, produces = "application/json")
	private String left(@PathVariable Long id, @RequestBody JSONObject data) throws Exception {
		LOG.trace("Id[{}] value left {}", id, data);

		service.save(id, data.getData(), HorizontalOrientationEnum.LEFT);
		String message = buildJsonResponse("Document left-side saved successfuly");

		LOG.trace("Id[{}] {}", id, message);

		return message;
	}

	/**
	 * 
	 * Right side comparison
	 *
	 * @author <a href="mailto:wellington.cs@hotmail.com">Wellington Silva</a>.
	 * @param id   unique identifier
	 * @param data string based base64
	 * @return String message response
	 * @throws Exception
	 */
	@RequestMapping(value = "/right", method = RequestMethod.POST, produces = "application/json")
	private String right(@PathVariable Long id, @RequestBody JSONObject data) throws Exception {
		LOG.trace("Id[{}] value right {}", id, data);

		service.save(id, data.getData(), HorizontalOrientationEnum.RIGHT);
		String message = buildJsonResponse("Document right-side successfuly");

		LOG.trace("Id[{}] {}", id, message);

		return message;
	}

	/**
	 * 
	 * Returns the comparison
	 *
	 * @author <a href="mailto:wellington.cs@hotmail.com">Wellington Silva</a>.
	 * @param id area identifier
	 * @return String message result
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	private String diff(@PathVariable Long id) {
		return buildJsonResponse(service.validateBase64(id));
	}

	/**
	 * 
	 * Create json message
	 *
	 * @author <a href="mailto:wellington.cs@hotmail.com">Wellington Silva</a>.
	 * @param value message
	 * @return String message json response
	 */
	private String buildJsonResponse(String value) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"");
		sb.append("message");
		sb.append("\":");
		sb.append("\"");
		sb.append(value);
		sb.append("\"");
		sb.append("}");
		return sb.toString();
	}
}
