package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerNFS {

    private static List<String> arquivos = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("----- ServerNFS -----");

        arquivos.add("arquivo1.txt");
        arquivos.add("arquivo2.txt");

        try (ServerSocket serverSocket = new ServerSocket(5555)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());

                new Thread(() -> {
                    try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                         DataInputStream dis = new DataInputStream(socket.getInputStream())) {
                            
                        while (true) {
                            int opcao = dis.readInt();

                            switch (opcao) {
                                case 1:
                                    listarArquivos(dos);
                                    break;
                                case 2:
                                    String novoArquivo = dis.readUTF();
                                    criarArquivo(novoArquivo);
                                    dos.writeUTF("Criando arquivo: " + novoArquivo);
                                    break;
                                case 3:
                                    String arquivoRemover = dis.readUTF();
                                    removerArquivo(arquivoRemover);
                                    dos.writeUTF("Removendo arquivo: " + arquivoRemover);
                                    break;
                                case 4:
                                    String nomeAntigo = dis.readUTF();
                                    String nomeNovo = dis.readUTF();
                                    renomearArquivo(nomeAntigo, nomeNovo);
                                    dos.writeUTF("Renomear arquivo de " + nomeAntigo + " para " + nomeNovo);
                                    break;
                                case 5:
                                default:
                                    System.out.println("Operação inválida");
                                    break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
    }
    }

    private static void listarArquivos(DataOutputStream dos) throws IOException {
        // Enviar lista de arquivos para o cliente
        for (String arquivo : arquivos) {
            dos.writeUTF(arquivo);
        }
        // Indicar fim da lista
        dos.writeUTF("");
    }

    private static void renomearArquivo(String nomeAntigo, String nomeNovo) {
        // Implementar a lógica para renomear o arquivo no sistema de arquivos real
        System.out.println("Renomear arquivo de " + nomeAntigo + " para " + nomeNovo);
        // Atualizar a lista de arquivos em memória
        int indice = arquivos.indexOf(nomeAntigo);
        if (indice != -1) {
            arquivos.set(indice, nomeNovo);
        }
    }

    private static void criarArquivo(String novoArquivo) {
        // Implementar a lógica para criar um novo arquivo no sistema de arquivos real
        System.out.println("Criar arquivo: " + novoArquivo);
        // Adicionar o novo arquivo à lista em memória
        arquivos.add(novoArquivo);
    }

    private static void removerArquivo(String arquivoRemover) {
        // Implementar a lógica para remover um arquivo no sistema de arquivos real
        System.out.println("Remover arquivo: " + arquivoRemover);
        // Remover o arquivo da lista em memória
        arquivos.remove(arquivoRemover);
    }
}

