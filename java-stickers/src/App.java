import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
 
public class App {

    public static void main(String[] args) throws Exception {

        // API api = API.IMDB_TOP_MOVIES;
        
        // String url = api.getUrl();
        // ExtratorDeConteudo extrator = api.getExtrator();

        String url = "http://localhost:8080/linguagens";
        ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        var diretorio = new File("figurinhas/");
        diretorio.mkdir();
  
        // Exibir e manipular os dados
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var geradora = new GeradoraDeFigurinhas();
        for (Conteudo conteudo : conteudos) {

            String textoFigurinha;
            InputStream imagemYann; 
            // if (conteudo.nota() >= 9.0) {
                textoFigurinha = "LOUCURA";
                imagemYann = new FileInputStream(new File("sobreposicao/download.jpeg"));
            /* } else {
                textoFigurinha = "BOMBOM";
                imagemYann = new FileInputStream(new File("sobreposicao/top.png"));
            } */

            InputStream inputStream = new URL(conteudo.urlImagem()).openStream();
            String nomeArquivo = "figurinhas/" + conteudo.titulo() + ".png";

            geradora.cria(inputStream, nomeArquivo, textoFigurinha, imagemYann);

            System.out.println("\u001b[36m \u001b[40m \u001b[1mTítulo:\u001b[m " + conteudo.titulo());
            System.out.println("\u001b[35m \u001b[40m \u001b[1mURL da Imagem:\u001b[m " + conteudo.urlImagem());
            // System.out.println("\u001b[33m \u001b[40m \u001b[1mNota:\u001b[m " + conteudo.getNota());
            System.out.println();
        }
    }
}
