package ascii_art;

public final class Loser {

    public final static String LOOSER = "\n" +
            "⢰⣶⣶⣶⣶⣾⡷⠀⠀⠀⠀ ⠀⣠⣾⣾⣿⣿⣿⣿⣷⣶⣤⡀⠀⠀⠀⢀⣿⣶⣿⣿⣿⣿⣿⣿⣶⣤⡀⠀⠀⢰⣿⣶⣾⣶⣶⣶⣶⣾⣿⡷⠀⢠⣿⣿⣶⣶⣶⣶⣶⣶⣶⣶⣤⡀⠀⠀\n" +
            "⢸⣿⣿⣿⣿⣿⡇⠀⠀ ⠀⠀⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡀⠀⢀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡆⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠐⠺⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡄⠀\n" +
            "⢸⣿⣿⣿⣿⣿⡇⠀⠀⠀ ⢸⣿⣿⣿⣿⣿⠏⠈⣿⣿⣿⣿⣿⣷⡀⣼⣿⣿⣿⣿⣿⠁⠈⣿⣿⣿⣿⣿⡇⠀⢼⣿⣿⣿⣿⣿⣿⠟⠿⠿⠏⠀⢘⣿⣿⣿⣿⣿⡟⠙⣿⣿⣿⣿⣿⣇⠀\n" +
            "⢸⣿⣿⣿⣿⣿⡇⠀⠀⠀ ⣿⣿⣿⣿⣿⣿⠆⠀⣿⣿⣿⣿⣿⣿⡓⣿⣿⣿⣿⣿⣿⡀⠀⢻⣿⣿⣿⣿⡗⠀⢸⣿⣿⣿⣿⣿⣧⠀⠀⠀⠀⢠⢸⣿⣿⣿⣿⣿⡇⠀⣹⣿⣿⣿⣿⡿⠀\n" +
            "⢸⣿⣿⣿⣿⣿⡇⠀⠀  ⣿⣿⣿⣿⣿⣿⡅⠀⣿⣿⣿⣿⣿⣿⡇⢿⣿⣿⣿⣿⣿⣿⣦⣀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣯⠀⠀⢀⡀⢠⢾⣿⣿⣿⣿⣿⣇⢀⣽⣿⣿⣿⣿⡗⠀\n" +
            "⢸⣿⣿⣿⣿⣿⡇⠀⠀⠀ ⣿⣿⣿⣿⣿⣿⠆⠀⣿⣿⣿⣿⣿⣿⡇⠈⠿⣿⣿⣿⣿⣿⣿⣿⣷⣦⡀⠀⠀⠀⢾⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠛⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⠃⠀\n" +
            "⢸⣿⣿⣿⣿⣿⡇⠀⠀⠀ ⣿⣿⣿⣿⣿⣿⡃⠀⣿⣿⣿⣿⣿⣿⡇⡀⠀⠈⠛⢿⣿⣿⣿⣿⣿⣿⣿⣶⡀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⢘⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣤⡀⠀\n" +
            "⢸⣿⣿⣿⣿⣿⡇⠀⠀⠀ ⣿⣿⣿⣿⣿⣿⡁⠀⣿⣿⣿⣿⣿⣿⡇⠁⣄⠀⠀⠀⠉⠻⣿⣿⣿⣿⣿⣿⣇⠀⣾⣿⣿⣿⣿⣿⡟⠉⠉⠉⠁⠀⢸⣿⣿⣿⣿⣿⡇⠈⣿⣿⣿⣿⣿⡗⠀\n" +
            "⢸⣿⣿⣿⣿⣿⡇⠀⠀⠀ ⣿⣿⣿⣿⣿⣿⡁⠀⣿⣿⣿⣿⣿⣿⡇⣼⣿⣿⣿⣿⣿⠇⠈⣿⣿⣿⣿⣿⣿⡏⣿⣿⣿⣿⣿⣿⡟⠀⠀⠀⠀⢀⣾⣿⣿⣿⣿⣿⡇⠀⣽⣿⣿⣿⣿⣏⠀\n" +
            "⢺⣿⣿⣿⣿⣿⣇⠀⢀⡀⠘⢻⣿⣿⣿⣿⣿⡁⠀⣿⣿⣿⣿⣿⣿⠃⠻⣿⣿⣿⣿⣿⠄⠀⢽⣿⣿⣿⣿⣿⠀⢹⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠈⢹⣿⣿⣿⣿⣿⡇⠀⣾⣿⣿⣿⣿⡇⠀\n" +
            "⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿ ⠘⣿⣿⣿⣿⣿⣷⣴⣿⣿⣿⣿⣿⡟⠀⠀⢿⣿⣿⣿⣿⣷⣤⣾⣿⣿⣿⣿⡟⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⣼⣿⣿⣿⣿⣿⣇⠀⢺⣿⣿⣿⣿⣿⠀\n" +
            "⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠙⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠀⠀⠀⠘⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠃⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⢨⣿⣿⣿⣿⣿⡇⠀⢺⣿⣿⣿⣿⣿⠄\n" +
            "⠘⠛⠛⠛⠛⠛⠛⠛⠛⠟⠀⠀⠀ ⠉⠛⠻⠿⠿⠿⠟⠋⠀⠀⠀⠀⠀⠀⠀⠉⠛⠻⠿⠿⠿⠟⠛⠉⠀⠀⠀⠈⠛⠙⠛⠙⠛⠛⠙⠛⠛⠋⠀⠀⠛⠛⠋⠛⠛⠋⠀⠈⠛⠛⠛⠛⠋⠀\n";
}
