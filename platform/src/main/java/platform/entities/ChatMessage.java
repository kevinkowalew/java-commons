package platform.entities;

public class ChatMessage {
	private final String id;
	private final User sender;
	private final User recipient;
	private final String text;

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		String id = "";
		User sender = null;
		User recipient = null;
		String text = "";

		private Builder() {
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}

		public Builder setSender(User sender) {
			this.sender = sender;
			return this;
		}

		public Builder setRecipient(User recipient) {
			this.recipient = recipient;
			return this;
		}

		public Builder setText(String text) {
			this.text = text;
			return this;
		}

		public ChatMessage build() {
			return new ChatMessage(this);
		}
	}

	private ChatMessage(final Builder builder) {
		this.id = builder.id;
		this.sender = builder.sender;
		this.recipient = builder.recipient;
		this.text = builder.text;
	}

	public String getId() {
		return id;
	}

	public User getSender() {
		return sender;
	}

	public User getRecipient() {
		return recipient;
	}

	public String getText() {
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recipient == null) ? 0 : recipient.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatMessage other = (ChatMessage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (recipient == null) {
			if (other.recipient != null)
				return false;
		} else if (!recipient.equals(other.recipient))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
}
