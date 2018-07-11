package com.github.aprofromindia.entities;

import lombok.NonNull;

import java.util.*;

import static com.github.aprofromindia.entities.Patient.HealthStatus.*;
import static com.github.aprofromindia.entities.Patient.Medicine.*;

public class Patient {

    private static final int MIX_MED_SIZE = 2;
    private HealthStatus status;

    public Patient(@NonNull final HealthStatus status) {
        this.status = status;
    }

    @NonNull
    public HealthStatus treat(@NonNull final List<Medicine> medicines) {
        final Map<Medicine, HealthStatus> minStatusMap = new HashMap<>(MIX_MED_SIZE);
        HealthStatus currentStatus = status;
        boolean treated = false;

        for (final Medicine med : medicines) {
            if (currentStatus == X) return X;

            if (med.mixMed != null) minStatusMap.put(med.mixMed, med.minStatus);

            if (minStatusMap.containsKey(med)) {
                currentStatus = currentStatus.ordinal - minStatusMap.get(med).ordinal > 0 ?
                        currentStatus : minStatusMap.get(med);
                treated = false;
            }

            if (currentStatus.medicines.contains(med)) {
                currentStatus = currentStatus.resultStatus;
                treated = true;
            }
        }

        if (treated) return currentStatus;
        else return X;
    }

    public enum HealthStatus {
        H(0), F(1), D(2), T(3), X(4);

        private Set<Medicine> medicines;
        private HealthStatus resultStatus;
        private int ordinal;

        static {
            F.medicines = new HashSet<>(Arrays.asList(As, P));
            F.resultStatus = H;
            H.medicines = Collections.emptySet();
            H.resultStatus = H;
            D.medicines = Collections.singleton(I);
            D.resultStatus = D;
            T.medicines = Collections.singleton(An);
            T.resultStatus = H;
            X.medicines = Collections.emptySet();
            X.resultStatus = X;
        }

        HealthStatus(int ordinal) {
            this.ordinal = ordinal;
        }
    }

    public enum Medicine {
        As, An, I, P;

        private Medicine mixMed;
        private HealthStatus minStatus = H;

        static {
            I.mixMed = An;
            I.minStatus = F;
            P.mixMed = As;
            P.minStatus = X;
        }
    }
}
