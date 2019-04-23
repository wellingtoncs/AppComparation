package com.wcs.compare.service;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wcs.compare.entity.AreaEntity;
import com.wcs.compare.enums.HorizontalOrientationEnum;
import com.wcs.compare.repository.AreaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiffServiceTest {

	@InjectMocks
	private DiffService service;

	@Mock
	public AreaRepository repository;
	
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}	
	
	@Test
	public void areaNotFound() throws Exception {
		
		//preparation
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		
		//action
		String result = service.validateBase64(1L);
		
		//validation
		Assert.assertThat(result, Matchers.is("Area not found"));
	}
	
	@Test
	public void areaMissingRight() throws Exception {
		
		//preparation
		AreaEntity areaMock = new AreaEntity(1L, "Left", null);
		Mockito.doReturn(areaMock).when(repository).findById(Mockito.eq(1L));
		
		//action
		String result = service.validateBase64(1L);
		
		//validation
		Assert.assertThat(result, Matchers.is("Base64 missing"));
	}

	@Test
	public void areaMissingLeft() throws Exception {
		
		//preparation
		AreaEntity areaMock = new AreaEntity(1L, null, "Right");
		Mockito.doReturn(areaMock).when(repository).findById(Mockito.eq(1L));
		
		//action
		String result = service.validateBase64(1L);
		
		//validation
		Assert.assertThat(result, Matchers.is("Base64 missing"));
	}
	
	@Test
	public void areaEqual() throws Exception {
		
		//preparation
		AreaEntity areaMock = new AreaEntity(1L, "d2VsbGluZ3RvbmNz", "d2VsbGluZ3RvbmNz");
		Mockito.doReturn(areaMock).when(repository).findById(Mockito.eq(1L));
		
		//action
		String result = service.validateBase64(1L);
		
		//validation
		Assert.assertThat(result, Matchers.is("True"));
	}
	
	@Test
	public void areaDifferentSize() throws Exception {
		
		//preparation
		AreaEntity areaMock = new AreaEntity(1L, "d2VsbGluZ3RvbmNz", "d2VsbGluZ3RvbmNzMTA=");
		Mockito.doReturn(areaMock).when(repository).findById(Mockito.eq(1L));
		
		//action
		String result = service.validateBase64(1L);
		
		//validation
		Assert.assertThat(result, Matchers.is("Left Size 16 and Right Size 20"));
	}
	
	@Test
	public void areaDifferentOffset() throws Exception {
		
		//preparation
		AreaEntity areaMock = new AreaEntity(1L, "d2VsbGluZ3RvbmNzMjA=", "d2VsbGluZ3RvbmNzMTA=");
		Mockito.doReturn(areaMock).when(repository).findById(Mockito.eq(1L));
		
		//action
		String result = service.validateBase64(1L);
		
		//validation
		Assert.assertThat(result, Matchers.is("offsets 17"));
	}
	
	@Test
	public void notFound() throws Exception {
		
		//preparation
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(AreaEntity.class));
		
		//action
		AreaEntity areaSave = service.save(1L, "Left", HorizontalOrientationEnum.LEFT);
		
		//validation
		Assert.assertThat(areaSave.getId(), Matchers.is(1L));
		Assert.assertThat(areaSave.getLeft(), Matchers.is("Left"));
		Assert.assertThat(areaSave.getRight(), Matchers.isEmptyOrNullString());
	}
	
	@Test
	public void leftFound() throws Exception {
		
		//preparation
		AreaEntity areaMock = new AreaEntity(1L, null, "Right");
		Mockito.doReturn(areaMock).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(AreaEntity.class));
		
		//action
		AreaEntity areaSave = service.save(1L, "Left", HorizontalOrientationEnum.LEFT);
		
		//validation
		Assert.assertThat(areaSave.getId(), Matchers.is(1L));
		Assert.assertThat(areaSave.getLeft(), Matchers.is("Left"));
		Assert.assertThat(areaSave.getRight(), Matchers.is("Right"));
	}
	
	@Test
	public void rightFound() throws Exception {
		
		//preparation
		AreaEntity areaMock = new AreaEntity(1L, "Left", null);
		Mockito.doReturn(areaMock).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(AreaEntity.class));
		
		//action
		AreaEntity areaSave = service.save(1L, "Right", HorizontalOrientationEnum.RIGHT);
		
		//validation
		Assert.assertThat(areaSave.getId(), Matchers.is(1L));
		Assert.assertThat(areaSave.getLeft(), Matchers.is("Left"));
		Assert.assertThat(areaSave.getRight(), Matchers.is("Right"));
	}
	
	@Test
	public void leftNotFound() throws Exception {
		
		//preparation
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(AreaEntity.class));
		
		//action
		AreaEntity areaSave = service.save(1L, "Right", HorizontalOrientationEnum.RIGHT);
		
		//validation
		Assert.assertThat(areaSave.getId(), Matchers.is(1L));
		Assert.assertThat(areaSave.getRight(), Matchers.is("Right"));
		Assert.assertThat(areaSave.getLeft(), Matchers.isEmptyOrNullString());
	}
	
	@Test
	public void rightNotFound() throws Exception {
		
		//preparation
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(AreaEntity.class));
		
		//action
		AreaEntity areaSave = service.save(1L, "Left", HorizontalOrientationEnum.LEFT);
		
		//validation
		Assert.assertThat(areaSave.getId(), Matchers.is(1L));
		Assert.assertThat(areaSave.getLeft(), Matchers.is("Left"));
		Assert.assertThat(areaSave.getRight(), Matchers.isEmptyOrNullString());
	}
}
