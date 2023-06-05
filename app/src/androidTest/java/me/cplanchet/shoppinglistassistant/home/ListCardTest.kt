package me.cplanchet.shoppinglistassistant.home

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import me.cplanchet.shoppinglistassistant.mock.MockData.shoppingListFourItems
import me.cplanchet.shoppinglistassistant.mock.MockData.shoppingListNoItems
import me.cplanchet.shoppinglistassistant.mock.MockData.shoppingListOneItem
import me.cplanchet.shoppinglistassistant.mock.MockData.shoppingListThreeItems
import me.cplanchet.shoppinglistassistant.ui.home.ListCard
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme
import org.junit.Rule
import org.junit.Test


class ListCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun listCard_noItems_showsNoItems(){
        composeTestRule.setContent {
            ShoppingListAssistantTheme {
                ListCard(list = shoppingListNoItems)
            }
        }
        composeTestRule.onNodeWithText("No Items").assertExists()
        composeTestRule.onNodeWithText("No Items").onParent().onChildren().assertCountEquals(2) //Assert there is now items column
    }

    @Test
    fun listCard_oneItem_displaysItem(){
        composeTestRule.setContent {
            ShoppingListAssistantTheme {
                ListCard(list = shoppingListOneItem)
            }
        }
        composeTestRule.onNodeWithText("One Item").assertExists()
        composeTestRule.onNodeWithText("fake item 1").onParent().onChildren().assertCountEquals(4)  //should only have one item in list
    }

    @Test
    fun listCard_threeItems_displaysItemsWithNoEllipses(){
        composeTestRule.setContent {
            ShoppingListAssistantTheme {
                ListCard(list = shoppingListThreeItems)
            }
        }
        composeTestRule.onNodeWithText("Three Items").assertExists()
        composeTestRule.onNodeWithText("fake item 1").onParent().onChildren().assertCountEquals(8)  //two more list items and two more check boxes
    }

    @Test
    fun listCard_fourItems_displaysThreeItemsWithEllipses(){
        composeTestRule.setContent {
            ShoppingListAssistantTheme {
                ListCard(list = shoppingListFourItems)
            }
        }
        composeTestRule.onNodeWithText("Four Items").assertExists()
        composeTestRule.onNodeWithText("fake item 1").onParent().onChildren().assertCountEquals(9) //same as 3, but add ellipses
        composeTestRule.onNodeWithText("...").assertExists()
    }
}