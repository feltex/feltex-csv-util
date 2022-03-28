package br.com.feltex;

import br.com.feltex.exception.CabecalhoInvalidoException;
import br.com.feltex.modelo.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class ImportaClienteCsv {

    private static final String CABECALHO = "nome;email;telefone";

    private ImportaClienteCsv() {
    }

    public static List<Cliente> lerArquivo(final InputStream arquivoInput) throws CabecalhoInvalidoException {
        log.info("Processando o arquivo");

        var clientes = new ArrayList<Cliente>();
        try (var scanner = new Scanner(arquivoInput)) {
            scanner.useDelimiter("\n");
            var cabecalho = scanner.next();
            validarCabecalho(cabecalho);
            while (scanner.hasNext()) {
                adicionarCliente(scanner.next(), clientes);
            }
        }
        return clientes;
    }

    private static void validarCabecalho(String cabecalho) throws CabecalhoInvalidoException {
        if (StringUtils.isEmpty(cabecalho) || !CABECALHO.equals(cabecalho)) {
            throw new CabecalhoInvalidoException(cabecalho);
        }
    }

    private static void adicionarCliente(String linha, List<Cliente> clientes) {
        var campos = linha.split(";");
        if (campos == null || campos.length < 2) {
            log.warn("Linha inválida {}", linha);
        } else {
            clientes.add(new Cliente(campos[0], campos[1], campos[2]));
        }
    }
}
