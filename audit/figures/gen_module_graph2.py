SCALE = 2

# family: (row, color, title, member modules)
families = {
    "app":       (0, "#37474f", "app", ["app"]),
    "ui":        (1, "#5c6bc0", "UI", ["ui-common", "ui-home", "ui-scanner", "ui-saved"]),
    "domain":    (2, "#26a69a", "domena", ["domain-common", "domain-recipes", "domain-products"]),
    "ds-c":      (3, "#7cb342", "datasource (kontrakt)",
                  ["datasource-common", "datasource-recipes", "datasource-products", "datasource-translation"]),
    "barcode-c": (3, "#9e9d24", "barcode (kontrakt)", ["barcode-scanner"]),
    "ds-i":      (4, "#ef6c00", "datasource (impl.)",
                  ["datasource-recipes-network", "datasource-products-network", "datasource-translation-network"]),
    "barcode-i": (4, "#f9a825", "barcode (impl.)", ["barcode-scanner-local"]),
    "db":        (4, "#c62828", "Room", ["database"]),
    "retrofit":  (5, "#8e24aa", "retrofit / sieć",
                  ["retrofit-base", "retrofit-recipes", "retrofit-deepl", "retrofit-openfoodfacts"]),
    "model":     (6, "#546e7a", "modele (JVM)", ["model-recipes", "model-products", "model-deepl"]),
}

# aggregated, de-duplicated family-to-family edges derived from build.gradle.kts project() deps
edges = [
    ("app", "ui"),
    ("app", "ds-c"), ("app", "ds-i"), ("app", "retrofit"),      # violation: bypasses domain
    ("ui", "domain"),
    ("ui", "barcode-c"), ("ui", "barcode-i"),
    ("domain", "ds-c"),
    ("domain", "db"),                                            # violation: bypasses datasource
    ("domain", "model"),
    ("ds-i", "ds-c"), ("ds-i", "retrofit"), ("ds-i", "model"),
    ("barcode-i", "barcode-c"),
    ("ds-c", "model"),
    ("retrofit", "model"),
]
violations = {("app", "ds-c"), ("app", "ds-i"), ("app", "retrofit"), ("domain", "db")}

row_order = {
    0: ["app"],
    1: ["ui"],
    2: ["domain"],
    3: ["ds-c", "barcode-c"],
    4: ["ds-i", "barcode-i", "db"],
    5: ["retrofit"],
    6: ["model"],
}

BOX_W, BOX_H = 300 * SCALE, 92 * SCALE
ROW_GAP = 170 * SCALE
COL_GAP = 40 * SCALE
MARGIN_X, MARGIN_Y = 70 * SCALE, 60 * SCALE

LONG_EDGES = [(s, d) for s, d in edges if families[d][0] - families[s][0] >= 2]
LANE_GAP = 26 * SCALE
LANE_ZONE = (len(LONG_EDGES) + 1) * LANE_GAP

max_row_w = max(len(v) * BOX_W + (len(v) - 1) * COL_GAP for v in row_order.values())
canvas_w = MARGIN_X * 2 + max_row_w + LANE_ZONE
canvas_h = MARGIN_Y * 2 + (max(row_order.keys()) + 1) * ROW_GAP + 40 * SCALE

CONTENT_W = MARGIN_X * 2 + max_row_w
pos = {}
for row, keys in row_order.items():
    row_w = len(keys) * BOX_W + (len(keys) - 1) * COL_GAP
    start_x = MARGIN_X + (max_row_w - row_w) / 2
    y = MARGIN_Y + row * ROW_GAP
    for i, key in enumerate(keys):
        x = start_x + i * (BOX_W + COL_GAP)
        pos[key] = (x, y)


def anchor(key, side):
    x, y = pos[key]
    if side == "bottom":
        return x + BOX_W / 2, y + BOX_H
    if side == "top":
        return x + BOX_W / 2, y
    if side == "left":
        return x, y + BOX_H / 2
    if side == "right":
        return x + BOX_W, y + BOX_H / 2


def edge_path(src, dst):
    r1, r2 = families[src][0], families[dst][0]
    if r2 > r1:
        sx, sy = anchor(src, "bottom")
        ex, ey = anchor(dst, "top")
    else:
        sx, sy = anchor(src, "right")
        ex, ey = anchor(dst, "right")
    mx = (sx + ex) / 2
    my = (sy + ey) / 2
    return f"M {sx:.1f} {sy:.1f} Q {mx:.1f} {sy:.1f} {mx:.1f} {my:.1f} Q {mx:.1f} {ey:.1f} {ex:.1f} {ey:.1f}"


def elbow_path(src, dst, lane_x, corner=10 * SCALE):
    sx, sy = anchor(src, "right")
    ex, ey = anchor(dst, "right")
    return (f"M {sx:.1f} {sy:.1f} "
            f"L {lane_x - corner:.1f} {sy:.1f} "
            f"Q {lane_x:.1f} {sy:.1f} {lane_x:.1f} {sy + corner:.1f} "
            f"L {lane_x:.1f} {ey - corner:.1f} "
            f"Q {lane_x:.1f} {ey:.1f} {lane_x - corner:.1f} {ey:.1f} "
            f"L {ex:.1f} {ey:.1f}")


svg = []
svg.append(f'<svg xmlns="http://www.w3.org/2000/svg" width="{canvas_w:.0f}" height="{canvas_h:.0f}" '
            f'viewBox="0 0 {canvas_w:.0f} {canvas_h:.0f}" font-family="Calibri, Arial, sans-serif">')
svg.append(f'<rect x="0" y="0" width="{canvas_w:.0f}" height="{canvas_h:.0f}" fill="#ffffff"/>')

svg.append('<defs>')
svg.append('<marker id="arrow" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="6.5" markerHeight="6.5" orient="auto-start-reverse">'
            '<path d="M 0 0 L 10 5 L 0 10 z" fill="#90a4ae"/></marker>')
svg.append('<marker id="arrow-v" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="6.5" markerHeight="6.5" orient="auto-start-reverse">'
            '<path d="M 0 0 L 10 5 L 0 10 z" fill="#c62828"/></marker>')
svg.append('</defs>')

# short edges (adjacent-ish rows): smooth curve within the content area
short_edges = [e for e in edges if e not in LONG_EDGES]
for src, dst in short_edges:
    is_viol = (src, dst) in violations
    d = edge_path(src, dst)
    if is_viol:
        svg.append(f'<path d="{d}" fill="none" stroke="#c62828" stroke-width="{2.2*SCALE}" '
                    f'stroke-dasharray="{6*SCALE},{5*SCALE}" marker-end="url(#arrow-v)" opacity="0.95"/>')
    else:
        svg.append(f'<path d="{d}" fill="none" stroke="#90a4ae" stroke-width="{1.8*SCALE}" '
                    f'marker-end="url(#arrow)" opacity="0.9"/>')

# long-skip edges: routed as elbow connectors through dedicated lanes in the right margin
lane_base = CONTENT_W + LANE_GAP
for i, (src, dst) in enumerate(LONG_EDGES):
    is_viol = (src, dst) in violations
    lane_x = lane_base + i * LANE_GAP
    d = elbow_path(src, dst, lane_x)
    if is_viol:
        svg.append(f'<path d="{d}" fill="none" stroke="#c62828" stroke-width="{2.2*SCALE}" '
                    f'stroke-dasharray="{6*SCALE},{5*SCALE}" marker-end="url(#arrow-v)" opacity="0.95"/>')
    else:
        svg.append(f'<path d="{d}" fill="none" stroke="#90a4ae" stroke-width="{1.8*SCALE}" '
                    f'marker-end="url(#arrow)" opacity="0.9"/>')

for key, (row, color, title, members) in families.items():
    x, y = pos[key]
    svg.append(f'<rect x="{x:.1f}" y="{y:.1f}" width="{BOX_W}" height="{BOX_H}" rx="{10*SCALE}" '
                f'fill="{color}" fill-opacity="0.10" stroke="{color}" stroke-width="{2.2*SCALE}"/>')
    svg.append(f'<rect x="{x:.1f}" y="{y:.1f}" width="{BOX_W}" height="{26*SCALE}" rx="{10*SCALE}" fill="{color}"/>')
    svg.append(f'<rect x="{x:.1f}" y="{y+13*SCALE:.1f}" width="{BOX_W}" height="{13*SCALE}" fill="{color}"/>')
    svg.append(f'<text x="{x + BOX_W/2:.1f}" y="{y + 17.5*SCALE:.1f}" text-anchor="middle" '
                f'font-size="{13.5*SCALE}" font-weight="600" fill="#ffffff">{title}</text>')
    n = len(members)
    for i, m in enumerate(members):
        my = y + 26 * SCALE + (i + 0.85) * ((BOX_H - 26 * SCALE) / n)
        svg.append(f'<text x="{x + BOX_W/2:.1f}" y="{my:.1f}" text-anchor="middle" '
                    f'font-size="{10.5*SCALE}" fill="#263238">{m}</text>')

# legend
legend_y = canvas_h - 22 * SCALE
lx = MARGIN_X
svg.append(f'<line x1="{lx}" y1="{legend_y}" x2="{lx + 50*SCALE}" y2="{legend_y}" stroke="#c62828" '
            f'stroke-width="{2.2*SCALE}" stroke-dasharray="{6*SCALE},{5*SCALE}"/>')
svg.append(f'<text x="{lx + 58*SCALE}" y="{legend_y + 4*SCALE:.1f}" font-size="{11.5*SCALE}" fill="#333">'
           'naruszenie warstwowej struktury modułów (podrozdz. 1.4 / 3.1)</text>')

svg.append('</svg>')

out = "\n".join(svg)
with open("C:/Users/Sebastian/AndroidStudioProjects/recipe.me/audit/figures/module_graph_families.svg", "w", encoding="utf-8") as f:
    f.write(out)

print("canvas", canvas_w, canvas_h)
