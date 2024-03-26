package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MainWindowTest {

    @Test
    void useModuleView() {
        assertTrue(MainWindow.useModuleView("/list_classes"));
        assertTrue(MainWindow.useModuleView("/add_class module/CS2103T tutorial/T09"));
        assertTrue(MainWindow.useModuleView("/delete_class module/CS2103T tutorial/T09"));
        assertFalse(MainWindow.useModuleView("/list_students"));
    }

    @Test
    void useSortedView() {
        assertTrue(MainWindow.useSortedView("/sort_student"));
        assertTrue(MainWindow.useSortedView("/sort_student by/name"));
        assertTrue(MainWindow.useSortedView("/sort_student by/EMaiL"));
    }
}
