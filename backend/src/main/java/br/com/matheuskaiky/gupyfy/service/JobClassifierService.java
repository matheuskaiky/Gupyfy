package br.com.matheuskaiky.gupyfy.service;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

/**
 * A service dedicated to classifying and analyzing job data,
 * such as determining the seniority level from a job title.
 */
@Service
public class JobClassifierService {

    private static final Map<String, List<String>> SENIORITY_KEYWORDS = new LinkedHashMap<>();
    static {
        SENIORITY_KEYWORDS.put("intern", List.of("estagio", "estagiario", "estagiaria", "internship", "intern", "practicas", "pasante"));
        SENIORITY_KEYWORDS.put("junior", List.of("junior", "jr", "iniciante", "entry level", "beginner"));
        SENIORITY_KEYWORDS.put("mid_level", List.of("pleno", "pl", "mid level", "ml", "intermediate", "intermedia"));
        SENIORITY_KEYWORDS.put("senior", List.of("senior", "sr", "especialista", "specialist", "expert"));
        SENIORITY_KEYWORDS.put("principal", List.of("principal", "staff", "architect", "arquiteto"));
        SENIORITY_KEYWORDS.put("lead", List.of("lider", "leader", "lead", "tech lead", "team lead", "liderança"));
        SENIORITY_KEYWORDS.put("manager", List.of("gerente", "manager", "gerencia", "coordenador", "coordinator"));
    }

    private static final List<String> SENIORITY_ORDER = new ArrayList<>(SENIORITY_KEYWORDS.keySet());
    static {
        Collections.reverse(SENIORITY_ORDER);
    }

    private static final Pattern DIACRITICS_PATTERN = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    private static final Pattern PUNCTUATION_PATTERN = Pattern.compile("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]");

    public static Map<String, List<String>> getSeniorityKeywords() {
        return SENIORITY_KEYWORDS;
    }

    /**
     * Infers the job level based on keywords found in the job title.
     * The method first normalizes the title and then iterates through predefined
     * seniority levels and their associated keywords.
     *
     * @param title The title of the job to classify.
     * @return An {@link Optional} containing the standardized job level key (e.g., "senior"),
     * or an empty Optional if no keyword is found.
     */
    public Optional<String> inferJobLevelFromTitle(String title) {
        if (title == null || title.isBlank()) {
            return Optional.empty();
        }

        String normalizedTitle = normalizeText(title);
        List<String> foundLevels = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : SENIORITY_KEYWORDS.entrySet()) {
            String seniorityKey = entry.getKey();
            for (String keyword : entry.getValue()) {
                if (Pattern.compile("\\b" + Pattern.quote(keyword) + "\\b").matcher(normalizedTitle).find()) {
                    foundLevels.add(seniorityKey);
                    break;
                }
            }
        }

        if (foundLevels.isEmpty()) {
            return Optional.empty();
        }

        return foundLevels.stream()
                .max(Comparator.comparingInt(SENIORITY_ORDER::indexOf));
    }

    /**
     * Normalizes a string by converting it to lowercase, removing accents (diacritics),
     * and replacing common separators with spaces.
     *
     * @param text The text to be normalized.
     * @return The normalized text.
     */
    private String normalizeText(String text) {
        String lowerCaseText = text.toLowerCase();
        String replacedSeparators = lowerCaseText.replaceAll("[-/]", " ");
        String noPunctuation = PUNCTUATION_PATTERN.matcher(replacedSeparators).replaceAll("");
        String normalized = Normalizer.normalize(noPunctuation, Normalizer.Form.NFD);
        return DIACRITICS_PATTERN.matcher(normalized).replaceAll("");
    }
}