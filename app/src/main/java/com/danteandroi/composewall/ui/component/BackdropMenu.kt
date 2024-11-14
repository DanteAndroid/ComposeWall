package com.danteandroi.composewall.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danteandroi.composewall.MenuItem
import com.danteandroi.composewall.MenuItem.Companion.SafeMenus

/**
 * @author Dante
 * 2022/7/29
 */
@Composable
fun BackdropMenu(
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean = false,
    menus: List<MenuItem>,
    onMenuSelected: (index: Int) -> Unit = {}
) {
    Spacer(modifier = Modifier.height(4.dp))
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    if (isExpandedScreen) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            menus.forEachIndexed { index, menuItem ->
                Menu(
                    modifier = modifier
                        .background(
                            if (index == selectedIndex) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .weight(1f)
                        .height(48.dp)
                        .clickable {
                            selectedIndex = index
                            onMenuSelected.invoke(index)
                        },
                    menu = menuItem
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    } else {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            menus.forEachIndexed { index, menuItem ->
                Menu(
                    modifier = modifier
                        .background(
                            if (index == selectedIndex) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable {
                            selectedIndex = index
                            onMenuSelected.invoke(index)
                        },
                    menu = menuItem
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun Menu(modifier: Modifier = Modifier, menu: MenuItem) {
    Box(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            text = menu.name,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onPrimary
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BackdropMenuPreview() {
    BackdropMenu(menus = SafeMenus)
}