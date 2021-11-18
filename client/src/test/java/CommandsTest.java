import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utility.Commands;

public class CommandsTest {

    @Test
    void isAValidCommand_NullParameter_ShouldThrowException(){
        Assertions.assertThrows(NullPointerException.class, () -> Commands.isAValidCommand(null));
    }

    @Test
    void isAValidCommand_ValidCommandWithNoParameters_ShouldPass(){
        Assertions.assertTrue(Commands.isAValidCommand("help"));
    }

    @Test
    void isAValidCommand_InvalidCommandWithNoParameters_ShouldFail(){
        Assertions.assertFalse(Commands.isAValidCommand("123help123"));
    }

    @Test
    void isAValidCommand_ValidCommandWithParameters_ShouldPass(){
        Assertions.assertTrue(Commands.isAValidCommand("remove_by_id 55"));
    }

    @Test
    void isAValidCommand_ValidCommandWithInvalidParameters_ShouldFail(){
        Assertions.assertFalse(Commands.isAValidCommand("remove_by_id notId"));
    }


    @Test
    void isCommandHaveValidArgument_BothNullParameter_ShouldThrowException(){
        Assertions.assertThrows(NullPointerException.class, () -> Commands.isCommandHaveValidArgument(null, null));
    }

    @Test
    void isCommandHaveValidArgument_FirstNullParameter_ShouldThrowException(){
        Assertions.assertThrows(NullPointerException.class, () -> Commands.isCommandHaveValidArgument(null, "1"));
    }

    @Test
    void isCommandHaveValidArgument_SecondNullParameter_ShouldThrowException(){
        Assertions.assertThrows(NullPointerException.class, () -> Commands.isCommandHaveValidArgument("help", null));
    }

    @Test
    void isCommandHaveValidArgument_ValidParameters_ShouldPass(){
        Assertions.assertTrue(Commands.isCommandHaveValidArgument("remove_by_id", "0"));
    }

    @Test
    void isCommandHaveValidArgument_InvalidParameters_ShouldFail(){
        Assertions.assertFalse(Commands.isCommandHaveValidArgument("remove_grater", "text"));
    }




}
