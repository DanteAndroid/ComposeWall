package com.danteandroi.composewall.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

/**
 * @author Du Wenyu
 * 2022/8/6
 */
@Composable
fun OptionsDialog(
    modifier: Modifier = Modifier,
    optionsArray: Array<Pair<ImageVector, Int>>,
    onDismissRequest: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    AlertDialog(modifier = modifier,
        onDismissRequest = onDismissRequest,
        buttons = {
            Spacer(modifier = Modifier.size(12.dp))
            optionsArray.forEachIndexed { index, res ->
                Row(
                    Modifier
                        .clickable {
                            onItemClick.invoke(index)
                            onDismissRequest.invoke()
                        }
                        .padding(vertical = 6.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                ) {
                    Image(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        imageVector = res.first, contentDescription = "Option $index"
                    )
                    Spacer(
                        modifier = Modifier
                            .width(12.dp)
                            .alignByBaseline()
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = stringResource(id = res.second),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
        })

}