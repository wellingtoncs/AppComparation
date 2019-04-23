package com.wcs.compare.service;

import java.util.Arrays;

import javax.xml.bind.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wcs.compare.entity.AreaEntity;
import com.wcs.compare.enums.HorizontalOrientationEnum;
import com.wcs.compare.repository.AreaRepository;

@Service
public class DiffService {

	@Autowired
	public AreaRepository repository;

	private static final Logger LOG = LoggerFactory.getLogger(DiffService.class);

	/**
	 * 
	 * Save the object
	 *
	 * @author <a href="mailto:wellington.cs@hotmail.com">Wellington Silva</a>.
	 * @param id          unique identifier
	 * @param data        Json String
	 * @param orientation Horizontal Orientation Enum
	 * @return AreaEntity
	 * @throws Exception
	 * 
	 */
	public AreaEntity save(Long id, String data, HorizontalOrientationEnum orientation) throws Exception {
		AreaEntity area = null;
		if (validate(id, data)) {
			area = repository.findById(id);
			if (area == null) {
				area = new AreaEntity();
				area.setId(id);
			}

			if (HorizontalOrientationEnum.LEFT.equals(orientation)) {
				area.setLeft(data);
			} else if (HorizontalOrientationEnum.RIGHT.equals(orientation)) {
				area.setRight(data);
			} else {
				LOG.warn("Horizontal Orientation invalid.");
			}
			area = repository.save(area);
		}
		return area;
	}

	/**
	 * 
	 * Checks the data for area is correctly.
	 *
	 * @author <a href="mailto:wellington.cs@hotmail.com">Wellington Silva</a>.
	 * @param id   identifier area
	 * @param data to valid
	 * @return boolean
	 * @throws Exception void
	 */
	public boolean validate(Long id, String data) throws ValidationException {
		boolean isValid = true;

		LOG.trace("Validate id = {} and data = {}", id, data);

		if (StringUtils.isEmpty(data)) {

			LOG.warn("Data is blank or null");
			isValid = false;
		}

		LOG.trace("Data valid is {}", isValid);
		return isValid;
	}

	/**
	 * Compare Jsons is valid
	 * 
	 * @param id identifier area
	 * @return a string with comparison results
	 */
	public String validateBase64(Long id) {
		LOG.trace("Validate Base64 is id = {} ", id);

		AreaEntity area = repository.findById(id);
		if (area == null) {
			return "Area not found";
		}

		if (!StringUtils.isNotBlank(area.getLeft()) || !StringUtils.isNotBlank(area.getRight())) {
			return "Base64 missing";
		}

		byte[] bytesLeft = area.getLeft().getBytes();
		byte[] bytesRight = area.getRight().getBytes();

		boolean blnResult = Arrays.equals(bytesLeft, bytesRight);

		String offsets = "";

		if (blnResult) {
			return "True";
		} else if (bytesLeft.length != bytesRight.length) {
			return "Left Size " + bytesLeft.length + " and Right Size " + bytesRight.length;
		} else {
			byte different = 0;
			for (int index = 0; index < bytesLeft.length; index++) {
				different = (byte) (bytesLeft[index] ^ bytesRight[index]);
				if (different != 0) {
					offsets = offsets + " " + index;
				}
			}
		}
		return "offsets" + offsets;
	}

}
