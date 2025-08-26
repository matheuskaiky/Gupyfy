package br.com.matheuskaiky.gupyfy.mapper;

import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public String toFullStateName(String stateAcronym) {
        if (stateAcronym == null) {
            return null;
        }
        return switch (stateAcronym.toUpperCase()) {
            case "AC" -> "Acre";
            case "AL" -> "Alagoas";
            case "AP" -> "Amapá";
            case "AM" -> "Amazonas";
            case "BA" -> "Bahia";
            case "CE" -> "Ceará";
            case "DF" -> "Distrito Federal";
            case "ES" -> "Espírito Santo";
            case "GO" -> "Goiás";
            case "MA" -> "Maranhão";
            case "MT" -> "Mato Grosso";
            case "MS" -> "Mato Grosso do Sul";
            case "MG" -> "Minas Gerais";
            case "PA" -> "Pará";
            case "PB" -> "Paraíba";
            case "PR" -> "Paraná";
            case "PE" -> "Pernambuco";
            case "PI" -> "Piauí";
            case "RJ" -> "Rio de Janeiro";
            case "RN" -> "Rio Grande do Norte";
            case "RS" -> "Rio Grande do Sul";
            case "RO" -> "Rondônia";
            case "RR" -> "Roraima";
            case "SC" -> "Santa Catarina";
            case "SP" -> "São Paulo";
            case "SE" -> "Sergipe";
            case "TO" -> "Tocantins";
            default -> stateAcronym;
        };
    }

    public String toStateAbbreviation(String fullStateName) {
        if (fullStateName == null) {
            return null;
        }
        return switch (fullStateName.toLowerCase()) {
            case "acre" -> "AC";
            case "alagoas" -> "AL";
            case "amapá", "amapa" -> "AP";
            case "amazonas" -> "AM";
            case "bahia" -> "BA";
            case "ceará", "ceara" -> "CE";
            case "distrito federal" -> "DF";
            case "espírito santo", "espirito santo" -> "ES";
            case "goiás", "goias" -> "GO";
            case "maranhão", "maranhao" -> "MA";
            case "mato grosso" -> "MT";
            case "mato grosso do sul" -> "MS";
            case "minas gerais" -> "MG";
            case "pará", "para" -> "PA";
            case "paraíba", "paraiba" -> "PB";
            case "paraná", "parana" -> "PR";
            case "pernambuco" -> "PE";
            case "piauí", "piaui" -> "PI";
            case "rio de janeiro" -> "RJ";
            case "rio grande do norte" -> "RN";
            case "rio grande do sul" -> "RS";
            case "rondônia", "rondonia" -> "RO";
            case "roraima" -> "RR";
            case "santa catarina" -> "SC";
            case "são paulo", "sao paulo" -> "SP";
            case "sergipe" -> "SE";
            case "tocantins" -> "TO";
            default -> fullStateName;
        };
    }
}
