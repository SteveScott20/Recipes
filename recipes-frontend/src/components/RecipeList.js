import React from 'react';
import Recipe from "./Recipe";

function RecipeList(props) {
    return (
        <div>
            {props.recipes.map((recipe) => <Recipe key={recipe.id} recipe={recipe} favoriteRecipe={props.favoriteRecipe} editRecipe={props.editRecipe} deleteRecipe={props.deleteRecipe}/>)}
        </div>
    );
}

export default RecipeList;