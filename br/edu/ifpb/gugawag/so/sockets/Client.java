package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("----- Cliente -----");


        try(Socket socket = new Socket("127.0.0.1", 5555);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                menu();

                int opcao = scanner.nextInt();
                dos.writeInt(opcao);
                dos.flush();

                switch (opcao) {
                    case 1:
                        String resposta;
                        while (!(resposta = dis.readUTF()).isEmpty()) {
                            System.out.println(resposta);
                        }
                        break;
                    case 2:
                        System.out.println("Digite o nome do novo arquivo:");
                        String novoArquivo = scanner.next();
                
                        dos.writeUTF(novoArquivo);
                        dos.flush();
                
                        String resposta03 = dis.readUTF();
                        System.out.println(resposta03);
                        break;
                    case 3:
                        System.out.println("Digite o nome do arquivo a ser removido:");
                        String arquivoRemover = scanner.next();
                
                        dos.writeUTF(arquivoRemover);
                        dos.flush();
                
                        String resposta04 = dis.readUTF();
                        System.out.println(resposta04);
                        break;
                    case 4:
                        System.out.println("Digite o nome do arquivo antigo:");
                        String nomeAntigo = scanner.next();
                        System.out.println("Digite o novo nome do arquivo:");
                        String nomeNovo = scanner.next();
                
                        dos.writeUTF(nomeAntigo);
                        dos.writeUTF(nomeNovo);
                        dos.flush();
                
                        String resposta02 = dis.readUTF();
                        System.out.println(resposta02);
                        break;
                    case 5:
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void menu() {
        System.out.println("\nEscolha uma operação:");
        System.out.println("1 - Listar arquivos");
        System.out.println("2 - Criar arquivo");
        System.out.println("3 - Remover arquivo");
        System.out.println("4 - Renomear arquivo");
        System.out.println("5 - Sair");
        System.out.println("");
    }
}
