package ir.atsignsina.task.snapfood.app.service;

/**
 * The ETAService interface provides methods for calculating estimated time of arrival.
 */
public interface ETAService {
    /**
     * Calculates the estimated time of arrival.
     *
     * @return The estimated time of arrival in minutes.
     */
    int eta();
}