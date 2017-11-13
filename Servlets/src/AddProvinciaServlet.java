import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import paa.municipios.IProvincia;
import dao.DBAccessMunicipiosDAO;
import dao.Provincia;
/**
 * Clase encargada de gestionar la peticion de adhesión de una provincia, y realiza la petición a la bbdd
 * @author Javier Suarez Sanchez
 *
 */
public class AddProvinciaServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private DBAccessMunicipiosDAO bbdd;
	private boolean ok = false;
	
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);}
	
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		bbdd = new DBAccessMunicipiosDAO();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		//Recoge los parámetros de la url
		String nomProv = request.getParameter("name").replace("+"," ");
		int codProv = Integer.parseInt(request.getParameter("code"));
		
		//Añade la provincia
		ok = bbdd.addProvincia((IProvincia)new Provincia(nomProv, codProv));
		
		//Devuelve true si se ejecutó con éxito
		JSONObject objetoRespuesta = new JSONObject();
		objetoRespuesta.put("success", ok);
		out.println(objetoRespuesta.toJSONString());
		out.close();
		
		System.out.println("añadiendoProvincia_" +nomProv);}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {}
    @Override
    public String getServletInfo() {
        return "Este servlet proporciona info"
                + " sobre de una almacen de poblaciones";}
}
