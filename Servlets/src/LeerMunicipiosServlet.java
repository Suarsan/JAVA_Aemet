import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import paa.municipios.IMunicipio;
import dao.DBAccessMunicipiosDAO;
/**
 * Clase encargada de gestionar la peticion de lectura los municipios de una provincia, realiza la petición a la bbdd y devuelve los resultados
 * @author Javier Suarez Sanchez
 *
 */
public class LeerMunicipiosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBAccessMunicipiosDAO bbdd;
	private Collection<IMunicipio> municipiosList;

	public void init(ServletConfig servletConfig) throws ServletException {
	    super.init(servletConfig);}
	
	@SuppressWarnings("unchecked")
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		bbdd = new DBAccessMunicipiosDAO();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		//Declaraciones
		JSONObject main = new JSONObject();
		JSONArray municipiosArray = new JSONArray();
		JSONObject municipio;
		IMunicipio auxMun;
		Iterator<IMunicipio> it;
		
		//Recoge el parámetro de la url
		String codProvincia=request.getParameter("id");

		//Devuelve los municipios
		municipiosList = (ArrayList<IMunicipio>)bbdd.getMunicipios(Integer.parseInt(codProvincia));
		it = municipiosList.iterator();		
		
		//Añado una a una los municipios al array JSON
		while(it.hasNext()){
			auxMun = it.next();
			municipio = new JSONObject();
			municipio.put("nombre", auxMun.getNombre());
			JSONObject auxProv = new JSONObject();
			auxProv.put("nombre", bbdd.getProvincia(Integer.parseInt(codProvincia)).getNombre());
			auxProv.put("codigo", bbdd.getProvincia(Integer.parseInt(codProvincia)).getCodigo());
			municipio.put("provincia", auxProv);
			municipio.put("altitud", auxMun.getAltitud());
			municipio.put("codigo", auxMun.getCodigo());
			municipiosArray.add(municipio);}
		
		//Añado el array al campo del objeto con id "municipios"
		main.put("municipios", municipiosArray);
		
		out.println (main.toJSONString());
		out.close();
		
		System.out.println("leyendoMunicipiosde_" + bbdd.getProvincia(Integer.parseInt(codProvincia)).getNombre());} 
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {}
    @Override
    public String getServletInfo() {
        return "Este servlet proporciona info"
                + " sobre de una almacen de poblaciones";}
}
