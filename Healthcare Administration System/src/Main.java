import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Case {
    private int caseNumber;
    private String caseType;
    private String caseStatus;

    public Case(int caseNumber, String caseType) {
        this.caseNumber = caseNumber;
        this.caseType = caseType;
        this.caseStatus = "Pending"; // Default status
    }

    public int getCaseNumber() {
        return caseNumber;
    }

    public String getCaseType() {
        return caseType;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void updateCaseStatus(String newStatus) {
        this.caseStatus = newStatus;
    }
}

class Scheduler {
    private Map<Integer, String> schedule;

    public Scheduler() {
        schedule = new HashMap<>();
    }

    public void scheduleCase(int caseNumber, String dateTime) {
        schedule.put(caseNumber, dateTime);
    }

    public String getCaseSchedule(int caseNumber) {
        return schedule.get(caseNumber);
    }
}

class DocumentManager {
    private Map<Integer, List<String>> documents;

    public DocumentManager() {
        documents = new HashMap<>();
    }

    public void uploadDocument(int caseNumber, String document) {
        documents.computeIfAbsent(caseNumber, k -> new ArrayList<>()).add(document);
    }

    public List<String> getDocumentsForCase(int caseNumber) {
        return documents.getOrDefault(caseNumber, new ArrayList<>());
    }
}

public class Main {
    public static void main(String[] args) {
        Case case1 = new Case(1, "Civil");
        Case case2 = new Case(2, "Criminal");

        Scheduler scheduler = new Scheduler();
        scheduler.scheduleCase(case1.getCaseNumber(), "2023-10-10 09:00 AM");
        scheduler.scheduleCase(case2.getCaseNumber(), "2023-10-12 10:30 AM");

        DocumentManager documentManager = new DocumentManager();
        documentManager.uploadDocument(case1.getCaseNumber(), "Document1.pdf");
        documentManager.uploadDocument(case1.getCaseNumber(), "Document2.pdf");
        documentManager.uploadDocument(case2.getCaseNumber(), "Document3.pdf");

        // Update case status
        case1.updateCaseStatus("In Progress");

        // Display case details
        System.out.println("Case #" + case1.getCaseNumber() + " - Type: " + case1.getCaseType() + " - Status: " + case1.getCaseStatus());
        System.out.println("Scheduled Date and Time: " + scheduler.getCaseSchedule(case1.getCaseNumber()));
        System.out.println("Documents: " + documentManager.getDocumentsForCase(case1.getCaseNumber()));
}
}
