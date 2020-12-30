package life.midorin.info.lifers.command;

/**
 * コマンドの実行結果を表します。
 */
public enum CommandResult {
    SUCCESS(true),
    FAILURE(false),

    LOADING_ERROR(false),
    STATE_ERROR(false),
    INVALID_ARGS(false),
    NO_PERMISSION(false);

    private final boolean wasSuccess;

    CommandResult(boolean wasSuccess) {
        this.wasSuccess = wasSuccess;
    }

    public boolean wasFailure() {
        return !this.wasSuccess;
    }

}