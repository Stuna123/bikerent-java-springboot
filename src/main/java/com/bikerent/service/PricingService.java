// Service qui calcule le prix d'une réservation selon les plages horaires
package com.bikerent.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;

@Service
public class PricingService {

    // Tarif de la plage 00:00 -> 07:00
    private static final BigDecimal NIGHT_RATE = new BigDecimal("5.00");

    // Tarif de la plage 07:00 -> 17:00
    private static final BigDecimal DAY_RATE = new BigDecimal("7.50");

    // Tarif de la plage 17:00 -> 24:00
    private static final BigDecimal EVENING_RATE = new BigDecimal("10.00");

    // Calcule le prix total selon l'heure de début et l'heure de fin
    public BigDecimal calculatePrice(LocalTime startTime, LocalTime endTime) {

        // On convertit les heures en minutes pour faciliter les calculs
        int startMinutes = startTime.getHour() * 60 + startTime.getMinute();
        int endMinutes = endTime.getHour() * 60 + endTime.getMinute();

        BigDecimal total = BigDecimal.ZERO;

        // Segment 1 : chevauchement avec la plage 00:00 -> 07:00
        total = total.add(calculateSegmentPrice(startMinutes, endMinutes, 0, 7 * 60, NIGHT_RATE));

        // Segment 2 : chevauchement avec la plage 07:00 -> 17:00
        total = total.add(calculateSegmentPrice(startMinutes, endMinutes, 7 * 60, 17 * 60, DAY_RATE));

        // Segment 3 : chevauchement avec la plage 17:00 -> 24:00
        total = total.add(calculateSegmentPrice(startMinutes, endMinutes, 17 * 60, 24 * 60, EVENING_RATE));

        return total;
    }

    // Calcule le prix d'un segment donné si la réservation le traverse
    private BigDecimal calculateSegmentPrice(int reservationStart,
                                             int reservationEnd,
                                             int segmentStart,
                                             int segmentEnd,
                                             BigDecimal hourlyRate) {

        // Début réel du chevauchement
        int overlapStart = Math.max(reservationStart, segmentStart);

        // Fin réelle du chevauchement
        int overlapEnd = Math.min(reservationEnd, segmentEnd);

        // S'il n'y a pas de chevauchement, prix = 0
        if (overlapStart >= overlapEnd) {
            return BigDecimal.ZERO;
        }

        // Nombre de minutes réellement passées dans ce segment
        int minutesInSegment = overlapEnd - overlapStart;

        // Conversion en heures décimales
        BigDecimal hours = BigDecimal.valueOf(minutesInSegment)
                .divide(BigDecimal.valueOf(60), 2, java.math.RoundingMode.HALF_UP);

        // Prix du segment = heures * tarif horaire
        return hours.multiply(hourlyRate);
    }
}