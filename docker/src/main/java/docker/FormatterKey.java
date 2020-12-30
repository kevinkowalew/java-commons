package docker;

enum FormatterKey {
  IMAGE("image"),
  RESTART("restart"),
  PORTS("ports"),
  ENVIRONMENT("environment"),
  VOLUMES("volumes"),
  VERSION("version"),
  SERVICES("services");

  private final String text;

  FormatterKey(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
