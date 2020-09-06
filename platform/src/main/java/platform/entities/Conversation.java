package platform.entities;

import java.util.Set;

public class Conversation {
	final private String id;
	final private Set<User> users;

	private Conversation(final Builder builder) {
		this.id = builder.id;
		this.users = builder.users;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String id = "";
		private Set<User> users = null;

		private Builder() {
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}

		public Builder setUsers(Set<User> users) {
			this.users = users;
			return this;
		}

		public Conversation build() {
			return new Conversation(this);
		}
	}

	public String getId() {
		return id;
	}

	public Set<User> getUsers() {
		return users;
	}
}
