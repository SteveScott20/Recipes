import React, {useState} from 'react';

function Recipe(props) {

    const [editing, setEditing] = useState(false);
    const [newTitle, setNewTitle] = useState(props.recipe.title);
    const [newDescription, setNewDescription] = useState(props.recipe.description);
    const [newCalories, setNewCalories] = useState(props.recipe.calories);

    return (
        <div>
            {!editing
                ? <span>{props.recipe.title} | {props.recipe.description} | {props.recipe.calories} | {props.recipe.favorite.toString()}</span>
                : <form>
                        <input type={"text-box"} placeholder={"Title"} name={"title"}
                               onChange={(e) => setNewTitle(e.target.value)}/>
                        <input type={"text-box"} placeholder={"Description"} name={"description"}
                               onChange={(e) => setNewDescription(e.target.value)}/>
                        <input type={"text-box"} placeholder={"Calories"} name={"calories"}
                               onChange={(e) => setNewCalories(e.target.value)}/>
                </form>
            }
            <button onClick={() => props.favoriteRecipe(props.recipe)}>Favorite</button>
            <button onClick={() => {
                if(editing){
                    props.editRecipe({
                        id: props.recipe.id,
                        title: newTitle,
                        description: newDescription,
                        calories: newCalories,
                        favorite: props.recipe.favorite
                    })
                }
                setEditing(!editing)
            }}>{editing ? "Save" : "Edit"}</button>
            <button onClick={() => props.deleteRecipe(props.recipe)}>Delete</button>
        </div>
    );
}

export default Recipe;