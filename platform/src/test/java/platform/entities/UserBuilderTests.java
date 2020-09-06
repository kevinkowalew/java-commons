package platform.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserBuilderTests {
	@Test
	public void testConstruction() {
		// Arrange

		final String ID = "2130721";
		final String NAME = "John Doe";
		final String EMAIL = "john.doe@gmail.com";
		final String AVATAR_URL = "someurl.com";

		User.Builder sut = User.builder();
		sut.setId(ID);
		sut.setName(NAME);
		sut.setEmail(EMAIL);
		sut.setAvatarUrl(AVATAR_URL);

		// Act
		final User returnValue = sut.build();

		// Assert
		assertEquals(returnValue.getId(), ID);
		assertEquals(returnValue.getName(), NAME);
		assertEquals(returnValue.getEmail(), EMAIL);
		assertEquals(returnValue.getAvatarUrl(), AVATAR_URL);
	}
}
