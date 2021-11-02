import {useEffect, useState} from "react";
import axios from "axios";
import RecipeList from "./components/RecipeList";
import RecipeCreator from "./components/RecipeCreator";

function App() {

  const [loading, setLoading] = useState(true);
  const [recipes, setRecipes] = useState([]);

  useEffect(() => {
    getRecipes();
  }, []);

  async function getRecipes(){
    setLoading(true);
    await axios.get("http://localhost:8080/recipe")
        .then((value) => {
          setRecipes(value.data);
        });
    setLoading(false);
  }

  async function createRecipe(recipe){
    await axios.post("http://localhost:8080/recipe", recipe);
    await getRecipes();
  }

  async function favoriteRecipe(recipe){
    await axios.patch("http://localhost:8080/recipe/"+recipe.id, {favorite: !recipe.favorite});
    await getRecipes();
  }

  async function editRecipe(recipe){
    await axios.patch("http://localhost:8080/recipe/"+recipe.id, recipe);
    await getRecipes();
  }

  async function deleteRecipe(recipe){
    await axios.delete("http://localhost:8080/recipe/"+recipe.id);
    await getRecipes();
  }

  return (
      <div>
          {(!loading &&
              (<div>
                <RecipeCreator createRecipe={createRecipe}/>
                <RecipeList recipes={recipes} favoriteRecipe={favoriteRecipe} editRecipe={editRecipe} deleteRecipe={deleteRecipe}/>
              </div>))
          }
      </div>
  );
}

export default App;
