package com.davidtomas.taskyapp.features.auth.presentation.login

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testLoginScreenUi_initial_State() {
        composeRule.setContent {
            TaskyAppTheme {
                LoginScreen(
                    state = LoginState(),
                    onAction = {}
                )
            }
        }

        composeRule.onNodeWithText("Introduce your Credentials").assertIsDisplayed()
        composeRule.onNodeWithText("Email address").assert(hasText(String.EMPTY_STRING))
        composeRule.onNodeWithText("Password").assert(hasText(String.EMPTY_STRING))
    }

    @Test
    fun testLoginScreenUi_correctly_filled_fields() {
        composeRule.setContent {
            TaskyAppTheme {
                LoginScreen(
                    state = LoginState(
                        email = "test@dt.com",
                        password = "1234aBc.?",
                        isPasswordVisible = true
                    ),
                    onAction = {}
                )
            }
        }

        composeRule.onNodeWithText("Introduce your Credentials").assertIsDisplayed()
        composeRule.onNodeWithText("Email address").assert(hasText("test@dt.com"))
        composeRule.onNodeWithText("Password").assert(hasText("1234aBc.?"))
    }
}