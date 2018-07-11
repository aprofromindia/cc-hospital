package com.github.aprofromindia;

import com.github.aprofromindia.entities.Patient;
import lombok.NonNull;

import java.util.*;

public class App {
    public static void main(String[] args) {
        final App app = new App();

        final String[] healthStates = args[0].split(",");
        final List<Patient> patients = new ArrayList<>(healthStates.length);

        final String[] medNames = args[1].split(",");
        final List<Patient.Medicine> medicines = new ArrayList<>(medNames.length);


        for (String s : healthStates) {
            s = s.trim();
            if (s.length() > 0)
                patients.add(new Patient(Patient.HealthStatus.valueOf(s)));
        }

        for (String s : medNames) {
            s = s.trim();
            if (s.length() > 0)
                medicines.add(Patient.Medicine.valueOf(s));
        }

        final Map<Patient.HealthStatus, Integer> results = app.calculate(patients, medicines);

        for (Map.Entry<Patient.HealthStatus, Integer> res : results.entrySet()) {
            System.out.print(String.format("%s: %d, ", res.getKey(), res.getValue()));
        }
    }

    @NonNull
    private Map<Patient.HealthStatus, Integer> calculate(
            @NonNull final List<Patient> patients,
            @NonNull final List<Patient.Medicine> medicines) {

        final Map<Patient.HealthStatus, Integer> results = new HashMap<>();
        Arrays.asList(Patient.HealthStatus.values()).forEach(val -> results.put(val, 0));

        for (final Patient p : patients) {
            final Patient.HealthStatus status = p.treat(medicines);
            results.put(status, results.get(status) + 1);
        }
        return results;
    }
}
