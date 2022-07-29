package com.danteandroi.composewall.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danteandroi.composewall.MenuItem
import com.danteandroi.composewall.MenuItem.Companion.MainMenus

/**
 * @author Du Wenyu
 * 2022/7/29
 */
@Composable
fun BackdropMenu(
    modifier: Modifier = Modifier,
    menus: List<MenuItem>,
    onMenuSelected: (menu: MenuItem) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        menus.forEach {
            Menu(
                modifier = modifier
                    .height(54.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        onMenuSelected.invoke(it)
                    },
                menu = it
            )
        }
    }
}

@Composable
fun Menu(modifier: Modifier = Modifier, menu: MenuItem) {
    Text(
        modifier = modifier,
        text = menu.name,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        ),
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun BackdropMenuPreview() {
    BackdropMenu(menus = MainMenus)
}