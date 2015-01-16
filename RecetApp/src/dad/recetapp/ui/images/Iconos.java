package dad.recetapp.ui.images;




import javafx.scene.image.Image;



public class Iconos {
	
	private static final String ICONS_PATH="dad/recetapp/ui/images/";
	public static final Image ADD_ICON=loadIcon("add-icon-20x20.png");
	public static final Image ADDTAD_ICON=loadIcon("addTabIcon.png");
	public static final Image CLOSETAD_ICON=loadIcon("closeTabIcon.png");
	public static final Image CLOSETADOVER_ICON=loadIcon("closeTabOverIcon.png");
	public static final Image DELETE_ICON=loadIcon("delete-icon-20x20.png");
	public static final Image EDIT_ICON=loadIcon("edit-icon-20x20.png");
	public static final Image LOGO_ICON=loadIcon("logo.png");
	public static final Image INICIO_ICON=loadIcon("recetapp-inicio.png");
	
	public static Image loadIcon(String name){
		String url = Iconos.class.getClassLoader().getResource(ICONS_PATH + name).toString();
		
		return new Image(url);
		
	}
	
}
