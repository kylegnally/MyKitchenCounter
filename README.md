# MyKitchenCounter

## Author

Kyle Nally

This app will allow the user to enter free-text food ingredient search terms, submit them via a web service to the USDA Food Composition Database, and receive a list of possible ingredient matches for their search term. The user is them prompted to select the closest matching item from a scrolling list. When selected, the chosen ingredient is added to a TextView on the same screen as the ingredient entry. When finished entering ingredients, the user selects a button and the app queries the database and retrieves the nutrition information for the chosen ingredients as a JSON object, parses it, and displays the nutrition information for the selected ingredients.

### Notes

Data is currently not saved on screen rotation and the "Browse saved recipes" functionality is not yet implemented.

### Known issues

The app currently does not handle screen rotation and does not allow user to select measures of ingredients.