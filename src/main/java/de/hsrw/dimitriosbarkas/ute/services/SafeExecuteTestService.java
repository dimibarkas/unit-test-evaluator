package de.hsrw.dimitriosbarkas.ute.services;

import java.nio.file.Path;

public interface SafeExecuteTestService {

    Path safelyExecuteTestInTempFolder(String encodedTask, String encodedTest);

}
