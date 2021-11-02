import React from 'react';

function RecipeCreator(props) {
    return (
        <div onSubmit={(e) => {
            e.preventDefault();
            props.createRecipe({
                title: e.target.title.value,
                description: e.target.description.value,
                calories: e.target.calories.value,
                favorite: false
            })
        }}>
            <form>
                <input type={"text-box"} placeholder={"Title"} name={"title"}/>
                <input type={"text-box"} placeholder={"Description"} name={"description"}/>
                <input type={"text-box"} placeholder={"Calories"} name={"calories"}/>
                <input type={"submit"}/>
            </form>
        </div>
    );
}

export default RecipeCreator;