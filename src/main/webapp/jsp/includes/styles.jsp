<%-- Estilos base para todas las paginas --%>
<style>
* { margin: 0; padding: 0; box-sizing: border-box; }
body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    min-height: 100vh;
    padding: 20px;
}
.container {
    max-width: 1200px;
    margin: 0 auto;
    background: white;
    border-radius: 10px;
    box-shadow: 0 10px 40px rgba(0,0,0,0.2);
    overflow: hidden;
}
header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    padding: 30px;
}
header h1 { font-size: 2em; margin-bottom: 15px; }
nav { display: flex; gap: 15px; flex-wrap: wrap; }
main { padding: 30px; }
.btn {
    display: inline-block;
    padding: 12px 24px;
    border-radius: 6px;
    text-decoration: none;
    font-weight: 600;
    transition: all 0.3s;
    border: none;
    cursor: pointer;
    font-size: 14px;
}
.btn-primary { background: #667eea; color: white; }
.btn-primary:hover { background: #5568d3; }
.btn-secondary { background: #6c757d; color: white; }
.btn-secondary:hover { background: #5a6268; }
.btn-success { background: #28a745; color: white; }
.btn-success:hover { background: #218838; }
.btn-danger { background: #dc3545; color: white; }
.btn-danger:hover { background: #c82333; }
.btn-info { background: #17a2b8; color: white; }
.btn-info:hover { background: #138496; }
.btn-warning { background: #ffc107; color: #333; }
.btn-sm { padding: 8px 16px; font-size: 12px; }
.btn-link { background: rgba(255,255,255,0.2); color: white; padding: 10px 20px; border-radius: 6px; text-decoration: none; }
.btn-link:hover { background: rgba(255,255,255,0.3); }
.btn-outline { background: transparent; border: 2px solid #667eea; color: #667eea; }
.btn-outline:hover { background: #667eea; color: white; }
.btn-active { background: #667eea; color: white; }
.form-container { background: #f8f9fa; padding: 30px; border-radius: 8px; margin-bottom: 30px; }
.form-group { margin-bottom: 20px; }
.form-group label { display: block; font-weight: 600; margin-bottom: 8px; color: #333; }
.form-control {
    width: 100%;
    padding: 12px;
    border: 2px solid #ddd;
    border-radius: 6px;
    font-size: 14px;
}
.form-control:focus { outline: none; border-color: #667eea; }
.form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
.form-actions { display: flex; gap: 15px; margin-top: 30px; }
.alert { padding: 15px 20px; border-radius: 6px; margin-bottom: 20px; }
.alert-error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
.alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
.alert-warning { background: #fff3cd; color: #856404; border: 1px solid #ffeeba; }
.alert-info { background: #d1ecf1; color: #0c5460; border: 1px solid #bee5eb; }
.table { width: 100%; border-collapse: collapse; margin: 20px 0; }
.table th, .table td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
.table th { background: #667eea; color: white; font-weight: 600; }
.table tr:hover { background: #f5f5f5; }
.badge { padding: 6px 12px; border-radius: 20px; font-size: 12px; font-weight: 600; display: inline-block; }
.badge-danger { background: #dc3545; color: white; }
.badge-warning { background: #ffc107; color: #333; }
.badge-success { background: #28a745; color: white; }
.badge-info { background: #17a2b8; color: white; }
.card { background: white; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); margin-bottom: 20px; overflow: hidden; }
.card-header { background: #667eea; color: white; padding: 15px 20px; font-weight: 600; }
.card-body { padding: 20px; }
.empty-state { text-align: center; padding: 60px 20px; color: #666; }
.empty-state h3 { font-size: 1.5em; margin-bottom: 15px; }
.subtitle { color: #666; margin-bottom: 20px; }
.strategy-buttons { display: flex; gap: 10px; flex-wrap: wrap; margin: 20px 0; }
.controls-section { background: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 20px; }
.info-box { background: #e7f3ff; border-left: 4px solid #2196F3; padding: 20px; border-radius: 6px; margin-bottom: 20px; }
.info-box h3 { color: #2196F3; margin-bottom: 10px; }
@media (max-width: 768px) {
    .form-row { grid-template-columns: 1fr; }
    .strategy-buttons { flex-direction: column; }
    nav { flex-direction: column; }
}
</style>