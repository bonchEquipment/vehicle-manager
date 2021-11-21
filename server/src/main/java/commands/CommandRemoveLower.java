package commands;

import utility.CollectionEditor;

/**
 * remove all elements with lower id
 */
public class CommandRemoveLower implements CommandWithArgument {
    private CollectionEditor collectionEditor;
    private String userEnteredId;
    private int id;


    public CommandRemoveLower(CollectionEditor collectionEditor) {
        this.collectionEditor = collectionEditor;
    }

    @Deprecated
    @Override
    public String execute() {
        if (!isArgumentANumber(userEnteredId)) {
            return "invalid number format \"" + userEnteredId + "\", try better";
        } else {
            if (collectionEditor.removeElementsWithIdLowerThan(id))
                return "all elements with id lower that " + userEnteredId + " was successfully deleted";
            return "there is no elements with id lower that  " + userEnteredId;
        }

    }


    public String execute(String requesterLogin) {
        if (!isArgumentANumber(userEnteredId)) {
            return "invalid number format \"" + userEnteredId + "\", try better";
        } else {
            if (collectionEditor.removeElementsWithIdLowerThan(id, requesterLogin))
                return "all elements with id lower that " + userEnteredId + " was successfully deleted";
            return "there is no elements with id lower that  " + userEnteredId;
        }

    }


    @Override
    public String getDescription() {
        return "remove all elements with lower id";
    }

    private boolean isArgumentANumber(String argument) {
        try {
            id = Integer.parseInt(argument);
            return true;
        } catch (NumberFormatException e) {
            try {
                id = (int) Double.parseDouble(argument) + 1;
                return true;
            } catch (NumberFormatException exception) {
                return false;
            }

        }
    }


    public void setArgument(String argument) {
        userEnteredId = argument;
    }

    @Override
    public String getSyntacticsExample() {
        return getName() + " 48";
    }


}
