package me.cplanchet.shoppinglistassistant

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import me.cplanchet.shoppinglistassistant.data.entities.Category
import me.cplanchet.shoppinglistassistant.data.entities.Item
import me.cplanchet.shoppinglistassistant.data.entities.ListItem
import me.cplanchet.shoppinglistassistant.data.entities.ShoppingList
import me.cplanchet.shoppinglistassistant.ui.home.ListCard
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme
import org.junit.Rule
import org.junit.Test


class ListCardTest {
    val category: Category = Category(1, "fakeCategory")

    val item1 = Item(1, "fake item 1", category)
    val item2 = Item(2, "fake item 2", category)
    val item3 = Item(3, "fake item 3", category)
    val item4 = Item(4, "fake item 4", category)

    val listItem1 = ListItem(item1, 1f, "count", false)
    val listItem2 = ListItem(item2, 1f, "count", false)
    val listItem3 = ListItem(item3, 1f, "count", false)
    val listItem4 = ListItem(item4, 1f, "count", false)   //TODO: convert to mock repo data

    val shoppingListNoItems = ShoppingList(1, "No Items", listOf<ListItem>(), null)
    val shoppingListOneItem = ShoppingList(2, "One Item", listOf(listItem1), null)
    val shoppingListThreeItems = ShoppingList(2, "Three Items", listOf(listItem1, listItem2, listItem3), null)
    val shoppingListFourItems = ShoppingList(2, "Four Items", listOf(listItem1, listItem2, listItem3, listItem4), null)

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