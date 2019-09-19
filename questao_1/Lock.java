public interface Lock {
    void lock(LockVar lockVar);
    void unlock(LockVar lockVar);
}
