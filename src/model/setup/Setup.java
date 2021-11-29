package model.setup;

public class Setup {
    private static Setup setup;
    private SetupBoard setupBoard;

      public static Setup getInstance(){
        if(setup == null){
            setup = new Setup();
        }
        return setup;
    }
}
