package model;

  public enum Priority {
    HIGH(1),
    MEDIUM(2),
    LOW(3);

    private final int priorityWeight;

    Priority(int priorityWeight) {
      this.priorityWeight = priorityWeight;
    }

    public int getPriorityWeight() {
      return priorityWeight;
    }
}
