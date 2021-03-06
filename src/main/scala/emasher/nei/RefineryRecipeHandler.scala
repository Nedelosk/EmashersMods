package emasher.nei

import codechicken.lib.gui.GuiDraw
import codechicken.nei.{NEIServerUtils, PositionedStack}
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector
import scala.collection.JavaConversions._

class RefineryRecipeHandler extends BaseRecipeHandler {

  final val recipeId = "sockets.refinery"

  override def getRecipeName: String = StatCollector.translateToLocal( "item.socket_module.107.name" ) + " Module"

  case object CachedRefineryRecipe extends CachedRecipe {
    final val inputAmount = 2
    final val outputAmount = 1

    override def getResult: PositionedStack = {
      new PositionedStack( new ItemStack( emasher.blocks.Blocks.propellent ), 98, 18, false )
    }

    override def getIngredients: java.util.List[PositionedStack] = {
      List( new PositionedStack( new ItemStack( emasher.blocks.Blocks.naturalGas ), 44, 18, true ) )
    }
  }

  override def getGuiTexture: String = "eng_toolbox:textures/gui/nei-refinery.png"

  override def loadCraftingRecipes( result: ItemStack ): Unit = {
    if( NEIServerUtils.areStacksSameTypeCrafting( new ItemStack( emasher.blocks.Blocks.propellent ), result ) ||
      NEIServerUtils.areStacksSameTypeCrafting( new ItemStack( emasher.items.Items.vialFilled, 1, 1), result ) ) {
      arecipes.add( CachedRefineryRecipe )
    }
  }

  override def loadCraftingRecipes( id: String, result: Object* ): Unit = {
    if( id == recipeId && this.getClass == classOf[RefineryRecipeHandler] ) {
      arecipes.add( CachedRefineryRecipe )
    } else if( result.nonEmpty ) {
      result.head match {
        case res: ItemStack =>
          loadCraftingRecipes( res )
        case _ =>
      }
    }
  }

  override def loadUsageRecipes( ingredient: ItemStack ): Unit = {
    if( NEIServerUtils.areStacksSameTypeCrafting( new ItemStack( emasher.blocks.Blocks.naturalGas ), ingredient ) ||
      NEIServerUtils.areStacksSameTypeCrafting( new ItemStack( emasher.items.Items.vialFilled, 1, 0), ingredient ) ){
      arecipes.add( CachedRefineryRecipe )
    }
  }

  override def drawExtras( recipe: Int ): Unit = {
    GuiDraw.drawString( CachedRefineryRecipe.outputAmount + " mB", 116, 23, 0x808080, false )
    GuiDraw.drawString( CachedRefineryRecipe.inputAmount + " mB", 16, 23, 0x808080, false )
  }
}
