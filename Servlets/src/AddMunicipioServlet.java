import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import paa.municipios.IMunicipio;
import paa.municipios.IProvincia;
import dao.DBAccessMunicipiosDAO;
import dao.Municipio;
import dao.Provincia;
/**
 * Clase encargada de gestionar la peticion de adhesi�n de un municipio, y realiza la petici�n a la bbdd
 * @author Javier Suarez Sanchez
 *
 */
public class AddMunicipioServlet extends HttpServlet{
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
		
		//Recoge los par�metros de la url
		String nomMun = request.getParameter("name").replace("+"," ");
		String nomProvMun = request.getParameter("provName").replace("+"," ");
		int codProvMun = Integer.parseInt(request.getParameter("provCode"));
		IProvincia provMun = new Provincia(nomProvMun, codProvMun); 
		int altitud = Integer.parseInt(request.getParameter("altitude"));
		int codigo = Integer.parseInt(request.getParameter("code"));
		
		//A�ade el municipio
		ok = bbdd.addMunicipio((IMunicipio)new Municipio(nomMun, provMun, altitud, codigo));
		
		//Devuelve ok si se ejecut� con �xito
		JSONObject objetoRespuesta = new JSONObject();
		objetoRespuesta.put("success", ok);
		out.println(objetoRespuesta.toJSONString());
		out.close();
		
		System.out.println("a�adiendoMunicipio_"+nomMun);}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {}
    @Override
    public String getServletInfo() {
        return "Este servlet proporciona info"
                + " sobre de una almacen de poblaciones";}
}
