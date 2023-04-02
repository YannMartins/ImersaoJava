import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
 
public class App {

    public static void main(String[] args) throws Exception {

        API api = API.IMDB_MOST_POPULAR;
        
        String url = api.getUrl();

        ExtratorDeConteudo extrator = api.getExtrator();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        var diretorio = new File("figurinhas/");
        diretorio.mkdir();
  
        // exibir e manipular os dados
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var geradora = new GeradoraDeFigurinhas();
        for (Conteudo conteudo : conteudos) {

            String textoFigurinha;
            InputStream imagemYann; 
            // if (conteudo.getNota() >= 8.0) {
                textoFigurinha = "LOUCURA";
                imagemYann = new FileInputStream(new File("sobreposicao/Eu.jpeg"));
            /* } else {
                textoFigurinha = "MARROMENO";
                imagemYann = new FileInputStream(new File("sobreposicao/file.jpeg"));
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
