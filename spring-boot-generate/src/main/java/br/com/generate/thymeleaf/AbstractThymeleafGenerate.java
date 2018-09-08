package br.com.generate.thymeleaf;

import org.springframework.util.StringUtils;

import br.com.generate.ReadScaffoldInfo;

import java.util.HashMap;
import java.util.Map;


/**
 * @author NetoDevel
 */
public abstract class AbstractThymeleafGenerate extends ReadScaffoldInfo {
	
	private static final String TABS_INDEX = "					";
	private static final String TABS_FORM = "				";
	private static final String TABS_SHOW = "		";
	private static Map<String, String> HTML_TAGS;
    static
    {
        HTML_TAGS = new HashMap<>();
        HTML_TAGS.put("String", "text");
        HTML_TAGS.put("Integer", "number");
        HTML_TAGS.put("Date", "date");
        HTML_TAGS.put("Boolean", "checkbox");
    }
	public String generateThParameters(String parameters) {
		String [] params = parameters.split(" ");
		String thParameters = "";
		
		for (int i = 0; i < params.length; i++) {
			String [] nameAndType = params[i].split(":");
			thParameters += TABS_INDEX + "<th>" + StringUtils.capitalize(nameAndType[0]) + "</th> \n";
		}

		thParameters += TABS_INDEX + "<th>Action</th>";
		return thParameters;
	}
	
	
	
	public String generateTdParameters(String className, String parameters) {
		String [] params = parameters.split(" ");
		String tdParameters = "";
		for (int i = 0; i < params.length; i++) {
			String [] nameAndType = params[i].split(":");
			tdParameters += TABS_INDEX + "<td th:text=\"${" + className.toLowerCase() + "." + nameAndType[0] + "}\"></td> \n";
		}
		
		tdParameters += TABS_INDEX + "<td>\n";
		tdParameters += TABS_INDEX + "		<a th:href=\"@{/"+ className.toLowerCase() + "s/{id}(id = " + "${" + className.toLowerCase() + ".id}" + ")}\">Show</a> \n";
		tdParameters += TABS_INDEX + " 		<a th:href=\"@{/"+ className.toLowerCase() + "s/{id}/edit(id = " + "${" + className.toLowerCase() + ".id}" + ")}\">Edit</a> \n";
		tdParameters += TABS_INDEX + "		<a th:href=\"@{/"+ className.toLowerCase() + "s/{id}/delete(id = " + "${" + className.toLowerCase() + ".id}" + ")}\">Destroy</a> \n";
		tdParameters += TABS_INDEX + "</td>";
		return tdParameters;
	}

	public String generateInputParameters(String parameters) {
		String inputParameters = "";
		String [] params = parameters.split(" ");
		for (int i = 0; i < params.length; i++) {
			String [] nameAndType = params[i].split(":");
			
			inputParameters += TABS_FORM + "<div class=\"form-group\"> \n";
			inputParameters += TABS_FORM + "	<label for=\""+ nameAndType[0] +"\">"+ StringUtils.capitalize(nameAndType[0]) +"</label>  \n";
			inputParameters += TABS_FORM + "	<input id=\""+ nameAndType[0] +"\" name=\"" + nameAndType[0] + "\" type=\""+getTypeHtml(nameAndType[1])+"\" class=\"form-control\" th:field=\"*{"+ nameAndType[0] +"}\" />  \n";
			inputParameters += TABS_FORM + "	<label th:if=\"${#fields.hasErrors('"+nameAndType[0]+"')}\" th:errors=\"*{"+nameAndType[0]+"}\" class=\"text-danger\"></label>";
			inputParameters += TABS_FORM + "</div> \n";
		}
		
		return inputParameters;
	}
	
	public String generateShowParameters(String paramClassName, String parameters) {
		String inputParameters = "";
		String [] params = parameters.split(" ");
		for (int i = 0; i < params.length; i++) {
			String [] nameAndType = params[i].split(":");
			inputParameters += TABS_SHOW + "<div class=\"form-group\"> \n";
			inputParameters += TABS_SHOW + "	<label for=\""+ nameAndType[0] +"\">"+ StringUtils.capitalize(nameAndType[0]) +": </label>  \n";
			inputParameters += TABS_SHOW + "	<span th:text=\"${" + paramClassName + "." + nameAndType[0] + "}\"></span> \n";
			inputParameters += TABS_SHOW + "</div> \n";
		}
		return inputParameters;
	}

	public String getTypeHtml(String javaType)
	{
        return HTML_TAGS.get(javaType);
	}
	
}
