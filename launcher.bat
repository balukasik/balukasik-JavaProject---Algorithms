@ECHO OFF
                                                                                            cd {project path}
                                                                                            java --module-path {javafx Path} --add-modules=javafx.controls,javafx.fxml -jar launcher.jar