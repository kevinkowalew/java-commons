package platform.entities;

public class User {
	private final String id;
	private final String name;
	private final String email;
	private final String password;
	private final String avatarUrl;

	private User(final Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.email = builder.email;
		this.password = builder.password;
		this.avatarUrl = builder.avatarUrl;
	}

	public static Builder builder() {
		return new Builder();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User user = (User) o;

		if (id != null ? !id.equals(user.id) : user.id != null)
			return false;
		if (name != null ? !name.equals(user.name) : user.name != null)
			return false;
		if (email != null ? !email.equals(user.email) : user.email != null)
			return false;
		if (password != null ? !password.equals(user.password) : user.password != null)
			return false;
		return avatarUrl != null ? avatarUrl.equals(user.avatarUrl) : user.avatarUrl == null;
	}

	public static class Builder {
		String id = "";
		String name = "";
		String email = "";
		String password = "";
		String avatarUrl = "";

		Builder() {
		}

		public Builder setId(final String id) {
			this.id = id;
			return this;
		}

		public Builder setName(final String name) {
			this.name = name;
			return this;
		}

		public Builder setEmail(final String email) {
			this.email = email;
			return this;
		}

		public Builder setPassword(final String password) {
			this.password = password;
			return this;
		}

		public Builder setAvatarUrl(final String avatarUrl) {
			this.avatarUrl = avatarUrl;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public String getAvatarUrl() {
		return this.avatarUrl;
	}
}
