package com.github.aprofromindia.entities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class PatientTest {

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {Patient.HealthStatus.F, Collections.singletonList(Patient.Medicine.P),
                        Patient.HealthStatus.H},
                {Patient.HealthStatus.H, Collections.singletonList(Patient.Medicine.P),
                        Patient.HealthStatus.H},
                {Patient.HealthStatus.F, Collections.singletonList(Patient.Medicine.As),
                        Patient.HealthStatus.H},
                {Patient.HealthStatus.T, Collections.singletonList(Patient.Medicine.An),
                        Patient.HealthStatus.H},
                {Patient.HealthStatus.D, Collections.singletonList(Patient.Medicine.I),
                        Patient.HealthStatus.D},
                {Patient.HealthStatus.F, Arrays.asList(Patient.Medicine.P, Patient.Medicine.As),
                        Patient.HealthStatus.X},
                {Patient.HealthStatus.H, Arrays.asList(Patient.Medicine.I,
                        Patient.Medicine.An, Patient.Medicine.I), Patient.HealthStatus.X},
                {Patient.HealthStatus.H, Arrays.asList(Patient.Medicine.P, Patient.Medicine.As,
                        Patient.Medicine.I, Patient.Medicine.As),
                        Patient.HealthStatus.X},
        };
    }

    @Parameterized.Parameter
    public Patient.HealthStatus given;

    @Parameterized.Parameter(1)
    public List<Patient.Medicine> given2;

    @Parameterized.Parameter(2)
    public Patient.HealthStatus then;


    @Test
    public void test_status_with_medicines() {
        // given
        final Patient patient = new Patient(given);

        // when
        final Patient.HealthStatus status = patient.treat(given2);

        // then
        assertThat(status).isEqualByComparingTo(then);
    }
}