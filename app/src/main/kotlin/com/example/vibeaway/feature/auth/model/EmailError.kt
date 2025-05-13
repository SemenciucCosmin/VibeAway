
import androidx.annotation.StringRes
import com.example.vibeaway.R

/**
 * Enum class for all possible states of the email text field.
 */
enum class EmailError(@StringRes val label: Int) {
    EMPTY(R.string.lbl_empty_email),
    INVALID(R.string.lbl_invalid_email),
    COLLISION(R.string.lbl_email_collision),
    NONE(R.string.lbl_empty)
}
